package allegro.service.threads;

import allegro.service.ResultContaioner;
import allegro.service.tasks.TaskParametersProvider;
import allegro.service.tasks.TaskResult;

import com.sun.xml.ws.fault.ServerSOAPFaultException;
import https.webapi_allegro_pl.service.ArrayOfFilteroptionstype;
import https.webapi_allegro_pl.service.DoGetItemsListRequest;
import https.webapi_allegro_pl.service.DoGetItemsListResponse;
import https.webapi_allegro_pl.service.SortOptionsType;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import allegro.tools.ProviderUtilBean;
import https.webapi_allegro_pl.service.FiltersListType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author ringo99<amid123@gmail.com>
 */
// klasa reprezentująca wątek pojedynczego wyszukiwania, jest ziarnem prototype
// Szuka pierwszego rezultatu
// Wątki do poprawdnego działania muszą otrzymać instancje ziarna ResultContainerBean dla odpowiedniej sesji
// i id tej sesji reprezentowana przez Stringa
@Service
@Scope("prototype")
public class SearchThreadBean extends Thread implements Serializable {

    private static final Logger LOG = Logger.getLogger(SearchThreadBean.class.getName());

    ProviderUtilBean util;

    private static int index = 0;

    private int indexOffset = 0;

    public int getIndexOffset() {
        return indexOffset;
    }

    public void setIndexOffset(int indexOffset) {
        this.indexOffset = indexOffset;
    }

    @Autowired
    public void setUtil(ProviderUtilBean util) {
        this.util = util;
    }

    private TaskResult newInstanceOfTaskResultBean() {
        return this.util.getBeanByType(TaskResult.class);
    }

    ResultContaioner results;

    private TaskParametersProvider parameters;

    private final DoGetItemsListRequest request;
    private DoGetItemsListResponse response;

    public SearchThreadBean() {
        this.request = new DoGetItemsListRequest();
    }

    // Singletonowy monitor, on wie czy mozna uruchomić nowy wątek czy nie
    SoapTimeMonitorService threadTimeMonitor;

    @Autowired
    public void setThreadTimeMonitor(SoapTimeMonitorService threadTimeMonitor) {
        this.threadTimeMonitor = threadTimeMonitor;
    }

    @Override
    public synchronized void run() {
        // Pobieramy instancje rezultatow dla tego zadania

        try {
            TaskResult taskResult = this.newInstanceOfTaskResultBean();
            //taskResult.setQueryId(this.parameters.getQueryId());
            request.setCountryId(this.parameters.getSearchCountrySelector());
            request.setWebapiKey(this.parameters.getApiKey());
            request.setResultOffset(indexOffset);

            request.setResultSize(this.parameters.getMaxResultsPerQuery());
            request.setResultScope(this.parameters.getSearchScopeSelector());
            
            
            //ustawiamy kraj i api key
            // Przekazujemy parametry jesli mamy nulle to inicjujemy nowym
            // obiektem
            try {
                request.setSortOptions(this.parameters.getSortOptionsType());
            } catch (NullPointerException ex) {
                request.setSortOptions(new SortOptionsType());
            }
            try {
                request.setFilterOptions(this.parameters.getFilterOptionsArray());
            } catch (NullPointerException ex) {
                request.setFilterOptions(new ArrayOfFilteroptionstype());
            }

            /*
             *   Musymy poczekac na wolne moce przerobowe wyszukiwarki
             *
             */
            this.threadTimeMonitor.activateTimeFlag();
            while (!this.threadTimeMonitor.isTimeFree()) {
                Thread.sleep(15);
            }

            /**
             * Wykonujemy zapytanie i przechwytujemy ew. błąd SOAP.
             */
            try {
                response = doGetItemsList(request);

                try {

                    // jeśli nie ma itemów na liscie to mamy filtry;
                    if (response.getItemsList() == null) {
                        taskResult.setQueryId(this.parameters.getQueryId());
                        taskResult.setArrayOfFilters(response.getFiltersList());
                        taskResult.setCategoriesList(response.getCategoriesList());
                        
                        for(FiltersListType flt :response.getFiltersList().getItem()){
                            LOG.info("Wyszukiwanie: " + this.parameters.getQueryId() + " Id filtra: " + flt.getFilterId() + " nazwa filtra: " + flt.getFilterName());
                        }
                        
                        /**
                         * Jeśli cos jest na liscie to mamy wyszukiwanie
                         */
                    } else {
                        taskResult.setQueryId(this.parameters.getQueryId());
                        taskResult.setItemsCount(response.getItemsCount());
                        taskResult.setArrayOfFilters(response.getFiltersList());
                        taskResult.getArrayOfItemsList().add(response.getItemsList());
                        taskResult.setCategoriesList(response.getCategoriesList());
                        taskResult.setItemsFeaturedCount(response.getItemsFeaturedCount());
                        taskResult.setRejectedFilters(response.getFiltersRejected());
                          
                        for(FiltersListType flt :response.getFiltersList().getItem()){
                            LOG.info("Wyszukiwanie: " + this.parameters.getQueryId() + " Id filtra: " + flt.getFilterId() + " nazwa filtra: " + flt.getFilterName());
                        }
                    
                    }
                } catch (NullPointerException ex) {
                    LOG.info("Blad w SearchThreadBean, ktorys z przekazwyanych parametrow == null");
                }

                // Dodajemy rezultat wyszukiwania do ResultContainera
                this.results.getTaskResultList().add(taskResult);

            } catch (ServerSOAPFaultException ex) {
                LOG.info("Blad w SOAPIE, tresc: ");
                LOG.info(ex.getMessage());
            }
            taskResult.setSearchDone(true);

        } catch (NullPointerException ex) {
            LOG.info("Blad, NPE w Watku wyszukiwania, ostateczny !!!!");
            LOG.info(ex.toString());
        } catch (IOException ex) {

        } catch (InterruptedException ex) {
            Logger.getLogger(SearchThreadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public TaskParametersProvider getParameters() {
        return parameters;
    }

    public void setParameters(TaskParametersProvider parameters) {
        this.parameters = parameters;
    }

    public ResultContaioner getResults() {
        return results;
    }

    public void setResults(ResultContaioner results) {
        this.results = results;
    }


    

    private static DoGetItemsListResponse doGetItemsList(https.webapi_allegro_pl.service.DoGetItemsListRequest parameters) throws IOException {
        https.webapi_allegro_pl.service.ServiceService service = new https.webapi_allegro_pl.service.ServiceService();
        https.webapi_allegro_pl.service.ServicePort port = service.getServicePort();
        return port.doGetItemsList(parameters);
    }

}
