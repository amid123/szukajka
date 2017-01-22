package allegro.service.threads;

import https.webapi_allegro_pl.service.ArrayOfFilteroptionstype;
import https.webapi_allegro_pl.service.DoGetItemsListRequest;
import https.webapi_allegro_pl.service.DoGetItemsListResponse;
import https.webapi_allegro_pl.service.FilterOptionsType;
import https.webapi_allegro_pl.service.ItemsListType;
import https.webapi_allegro_pl.service.SortOptionsType;
import java.util.logging.Logger;
import allegro.service.ResultContaioner;
import allegro.service.ResultContaioner;
import allegro.service.tasks.TaskResult;
import allegro.tools.ProviderUtilBean;
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
public class SearchGetListByFilterThreadBean extends Thread {

    private static final Logger LOG = Logger.getLogger(SearchGetListByFilterThreadBean.class.getName());

    ProviderUtilBean util;

    @Autowired
    public void setUtil(ProviderUtilBean util) {
        this.util = util;
    }

    private TaskResult newInstanceOfTaskResultBean() {
        return this.util.getBeanByType(TaskResult.class);
    }

    ResultContaioner results;

    private final int maxResultsPerQuery;

    private final int maxResult;
    private final int maxResultRedefined;
    private int offsetIndex;
    private int offsets;

    FilterOptionsType filterOptionsType;

    SortOptionsType sortOptionsType;
    ArrayOfFilteroptionstype filterOptionsArray;

    private String sessionId;

    public SearchGetListByFilterThreadBean() {
        this.sortOptionsType = new SortOptionsType();
        this.filterOptionsArray = new ArrayOfFilteroptionstype();
        this.maxResultRedefined = 5000;
        this.maxResultsPerQuery = 1000;
        this.offsetIndex = 0;
        this.maxResult = 2000;
        this.sessionId = null;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    // Singletonowy monitor, on wie czy mozna uruchomić nowy wątek czy nie
    SoapTimeMonitorService threadTimeMonitor;

    @Autowired
    public void setThreadTimeMonitor(SoapTimeMonitorService threadTimeMonitor) {
        this.threadTimeMonitor = threadTimeMonitor;
    }

    @Override
    public void run() {
        DoGetItemsListRequest request = new DoGetItemsListRequest();
        DoGetItemsListResponse response = new DoGetItemsListResponse();

        // Pobieramy instancje rezultatow dla tego zadania
        TaskResult taskResult = this.newInstanceOfTaskResultBean();

        this.sortOptionsType = new SortOptionsType();
        //this.filterOptionsArray = new ArrayOfFilteroptionstype();

        request.setCountryId(1);
        request.setWebapiKey("41e7acc7");
        if (sortOptionsType != null) {
            request.setSortOptions(sortOptionsType);
        } else {
            LOG.info("Blad: NPE, sortOptions==null");
        }
        if (filterOptionsArray != null) {
            request.setFilterOptions(filterOptionsArray);
        } else {
            LOG.info("Blad: NPE, filterOptionsArray==null");
        }
        request.setResultOffset(offsetIndex);
        request.setResultSize(maxResultsPerQuery);
        request.setResultScope(0); // pobieramy wszystkie dane

        /*
         *   Musymy poczekac na wolne moce przerobowe wyszukiwarki
         *
         */
        this.threadTimeMonitor.activateTimeFlag();
        while (!this.threadTimeMonitor.isTimeFree()) {
        }

        response = doGetItemsList(request);

        // Zachowujemy tez nasz request - moze się później przydać
        taskResult.setArrayOfFiltersRequested(filterOptionsArray);
        // Wypełniamy instancje rezultatow i przekzujemy do ziarna 
        // resultContainer dostepnego w całej sesji 
        taskResult.setArrayOfFilters(response.getFiltersList());

        taskResult.getArrayOfItemsList().add(response.getItemsList());

        taskResult.setCategoriesList(response.getCategoriesList());
        taskResult.setItemsCount(response.getItemsCount());
        taskResult.setItemsFeaturedCount(response.getItemsFeaturedCount());
        taskResult.setRejectedFilters(response.getFiltersRejected());

        this.results.getTaskResultList().add(taskResult);

        LOG.info("Ilosc pasujacych odpowiedzi: " + response.getItemsCount());

        try {
            for (ItemsListType item : response.getItemsList().getItem()) {
                LOG.info(item.getItemTitle());
            }

        } catch (NullPointerException ex) {
            LOG.info("Brak danych pasujących do twojego zapytania, spróbój ponownie");

        }

        // Jeśli jest więcej wyników niż w jednej porcji danych;
        if (response.getItemsCount() > this.maxResultsPerQuery) {
            // Jeśli jest mniej zapytań niż ustawiono maksymalnie;
            // Dzięki temu mamy możliwość ustawienia progu maksymalnej ilości zwrotek;
            if (response.getItemsCount() < this.maxResult) {
                // Ilość offsetów jest równa ilości wyników przez porcję danych - 1
                // dla modulo 0 a dla modulo 1, - 1
                if (response.getItemsCount() % maxResultsPerQuery == 0) {
                    offsets = this.maxResult / maxResultsPerQuery;
                } else {
                    offsets = this.maxResult / maxResultsPerQuery - 1;
                }

                // Lecimy az index offsetu bedzie rowny wyliczonej ilosci ofsetów
                do {
                    // Inkrementujemy bo zerowy już wykożsytaliśmy do pierwszego zapytania
                    offsetIndex++;

                    // Ustawiamy offset
                    request.setResultOffset(offsetIndex);
                    /*
                     *   Musymy poczekac na wolne moce przerobowe wyszukiwarki
                     *
                     */
                    this.threadTimeMonitor.activateTimeFlag();
                    while (!this.threadTimeMonitor.isTimeFree()) {
                    }
                    LOG.info("Wysłano zapytanie z watku");
                    response = doGetItemsList(request);
                    // Pobrane dane przekazujemy do lisy wyników
                    // Natępnie wrzucamy to do ziarna ResultContainer

                    for (ItemsListType item : response.getItemsList().getItem()) {
                        LOG.info(item.getItemTitle());
                    }
                    taskResult.getArrayOfItemsList().add(response.getItemsList());
                    this.results.getTaskResultList().add(taskResult);

                    LOG.info("offset " + offsetIndex);
                } while (offsetIndex != offsets);
                LOG.info("Wyszkano: " + offsets + " ofsetow.");

                // jeśli więcej
            } else {
                // Ilość offsetów jest równa ilości wyników przez porcję danych - 1
                // dla modulo 0 a dla modulo 1, - 1
                if (response.getItemsCount() % this.maxResultsPerQuery == 0) {
                    offsets = this.maxResultRedefined / maxResultsPerQuery;
                } else {
                    offsets = this.maxResultRedefined / maxResultsPerQuery - 1;
                }
                LOG.info("ilosc offsetow: " + offsets);

                // Lecimy az index offsetu bedzie rowny wyliczonej ilosci ofsetów
                do {
                    // Inkrementujemy bo zerowy już wykożsytaliśmy do pierwszego zapytania
                    offsetIndex++;

                    // Ustawiamy offset
                    request.setResultOffset(offsetIndex);
                    /*
                     *   Musymy poczekac na wolne moce przerobowe wyszukiwarki
                     *
                     */
                    this.threadTimeMonitor.activateTimeFlag();
                    while (!this.threadTimeMonitor.isTimeFree()) {
                    }
                    LOG.info("Wysłano zapytanie z watku");
                    response = doGetItemsList(request);
                    // Pobrane dane przekazujemy do lisy wyników
                    // Natępnie wrzucamy to do ziarna ResultContainer

                    for (ItemsListType item : response.getItemsList().getItem()) {
                        LOG.info(item.getItemTitle());
                    }

                    taskResult.getArrayOfItemsList().add(response.getItemsList());
                    this.results.getTaskResultList().add(taskResult);
                    LOG.info("offset " + offsetIndex);
                } while (offsetIndex != offsets);
                LOG.info("Wyszkano: " + offsets + " ofsetow.");
            }
        }
        // Ustawiamy flagę zakończonego zadania
        taskResult.setSearchDone(true);
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
