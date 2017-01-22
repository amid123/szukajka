package allegro.service.threads;

import https.webapi_allegro_pl.service.ArrayOfFilteroptionstype;
import https.webapi_allegro_pl.service.DoGetItemsListRequest;
import https.webapi_allegro_pl.service.DoGetItemsListResponse;
import https.webapi_allegro_pl.service.FilterOptionsType;
import https.webapi_allegro_pl.service.FiltersListType;
import https.webapi_allegro_pl.service.SortOptionsType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import allegro.service.ResultContaioner;
import allegro.service.ResultContaioner;
import allegro.service.tasks.TaskResult;
import allegro.tools.ProviderUtilBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
// klasa reprezentująca wątek pojedynczego wyszukiwania, jest ziarnem prototype
// Szuka pierwszego rezultatu
// Wątki do poprawdnego działania muszą otrzymać instancje ziarna ResultContainerBean dla odpowiedniej sesji
// i id tej sesji reprezentowana przez Stringa
@Service
@Scope("prototype")
public class SearchGetFiltersListThreadBean extends Thread {
    
    
    ProviderUtilBean util;

    @Autowired
    public void setUtil(ProviderUtilBean util) {
        this.util = util;
    }
    
    private TaskResult newInstanceOfTaskResultBean() {
        return this.util.getBeanByType(TaskResult.class);
    }
    
    private static final Logger LOG = Logger.getLogger(SearchGetFiltersListThreadBean.class.getName());
    
    ResultContaioner results;
    
    FilterOptionsType filterOptionsType;
    
    SortOptionsType sortOptionsType;
    ArrayOfFilteroptionstype filterOptionsArray;

    // Singletonowy monitor, on wie czy mozna uruchomić nowy wątek czy nie
    SoapTimeMonitorService threadTimeMonitor;
    
    public SearchGetFiltersListThreadBean() {
        this.filterOptionsType = new FilterOptionsType();
        this.sortOptionsType = new SortOptionsType();
        this.filterOptionsArray = new ArrayOfFilteroptionstype();
    }
    
    @Autowired
    public void setThreadTimeMonitor(SoapTimeMonitorService threadTimeMonitor) {
        this.threadTimeMonitor = threadTimeMonitor;
    }
    
    @Override
    public void run() {
        DoGetItemsListRequest request = new DoGetItemsListRequest();
        DoGetItemsListResponse response;
        
        // Pobieramy instancje rezultatow dla tego zadania
        TaskResult taskResult = this.newInstanceOfTaskResultBean();
        
        request.setCountryId(1);
        request.setWebapiKey("41e7acc7");
        
        this.filterOptionsType.setFilterId(null);
        
        //filterOptionsArray = new ArrayOfFilteroptionstype();

        if (filterOptionsArray != null) {
            request.setFilterOptions(filterOptionsArray);
        } else {
            LOG.info("Blad: NPE, filterOptionsArray==null z watku pobierajacego filtry");
        }
        request.setResultOffset(0);
        request.setResultSize(1);
        request.setResultScope(4); // nie zwracaj ofert

        /*
         *   Musymy poczekac na wolne moce przerobowe wyszukiwarki
         *
         */
        
        this.threadTimeMonitor.activateTimeFlag();
        while (!this.threadTimeMonitor.isTimeFree()) {
            try {
                Thread.sleep(15);
            } catch (InterruptedException ex) {
                Logger.getLogger(SearchGetFiltersListThreadBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        response = doGetItemsList(request);
        
        // Zachowujemy tez nasz request - moze się później przydać
        taskResult.setArrayOfFiltersRequested(filterOptionsArray);
        
        // Wypełniamy instancje rezultatow i przekzujemy do ziarna 
        // resultContainer dostepnego w całej sesji
        taskResult.setArrayOfFilters(response.getFiltersList());
        taskResult.setCategoriesList(response.getCategoriesList());
        //taskResult.setItemsCount(response.getItemsCount());
        //taskResult.setItemsFeaturedCount(response.getItemsFeaturedCount());
        taskResult.setRejectedFilters(response.getFiltersRejected());
        // Pobieramy listę beanow i wstrzykujemy rezultat
        this.results.getTaskResultList().add(taskResult); 
        
    }
    
    public ResultContaioner getResults() {
        return results;
    }
    
    public void setResults(ResultContaioner results) {
        this.results = results;
    }
    
    public ArrayOfFilteroptionstype getFilterOptionsArray() {
        return filterOptionsArray;
    }
    
    public void setFilterOptionsArray(ArrayOfFilteroptionstype filterOptionsArray) {
        this.filterOptionsArray = filterOptionsArray;
    }
    
    public SortOptionsType getSortOptionsType() {
        return sortOptionsType;
    }
    
    public void setSortOptionsType(SortOptionsType sortOptionsType) {
        this.sortOptionsType = sortOptionsType;
    }
    
    private static DoGetItemsListResponse doGetItemsList(https.webapi_allegro_pl.service.DoGetItemsListRequest parameters) {
        https.webapi_allegro_pl.service.ServiceService service = new https.webapi_allegro_pl.service.ServiceService();
        https.webapi_allegro_pl.service.ServicePort port = service.getServicePort();
        return port.doGetItemsList(parameters);
    }
    
}
