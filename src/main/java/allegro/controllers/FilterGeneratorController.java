/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.controllers;

import https.webapi_allegro_pl.service.ArrayOfFilteroptionstype;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import allegro.service.threads.SearchThreadBean;
import allegro.service.FilterFactoryService;
import allegro.service.RejectedFiltersCleanerService;
import allegro.service.ResultContaioner;
import allegro.service.tasks.SearchTaskType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import allegro.service.options.ApplicationOptionsProvider;
import allegro.service.tasks.TaskManager;
import allegro.service.tasks.TaskParametersProvider;
import allegro.service.tasks.impl.TaskParametersProviderImpl;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
/**
 * Kontroler tworzy nowe zadnie, pobiera dane z requestu, tworzy dla zadania
 * filtr i przekazuje go do TaskContainer - który jeszcze nie istnieje ;)
 */
@Controller
@Scope("request")
public class FilterGeneratorController {

    private static final Logger LOG = Logger.getLogger(FilterGeneratorController.class.getName());

    @Autowired
    FilterFactoryService filterFactory;
    @Autowired
    ResultContaioner resultContainer;
    @Autowired
    TaskManager taskManager2;
    //@Autowired
    // TaskManagerBean taskManager;
    @Autowired
    RejectedFiltersCleanerService rejectedFiltersCleaner;
    @Autowired
    ApplicationOptionsProvider appOptions;

    @RequestMapping(value = "/filterGenerator0", method = RequestMethod.POST)
    public ModelAndView filterGenerator0(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {

        this.resultContainer.setLastUsedFilterId(0);
        this.resultContainer.clearAllResults();

        TaskParametersProvider taskParameters = new TaskParametersProviderImpl();

        String searchQueryId = "search_query_0";

        /**
         * Musimy zapamiętać otrzymany filtr w sesji i kojarzyc go z konkretnym
         * filtrem w tym celu zapisujemy go do listy parametrów w result
         * container;
         *
         * 1. Musimy wiedzieć czy filtr istnieje czy nie Jeśli nie istnieje to
         * go tworzymy.
         *
         * Jeśli istnieje to sprawdzamy czy się zmienił Jeśli się nie zmienił, -
         * przyszedł request z pustym parametrem "search" to to zostawiamy stary
         * filtr
         *
         * Jeśli się zmienił to musimy stworzyć nowy filtr, usunąć stary filtr
         * posiadajace odpowiednie queryId i dodać nowy filtr
         *
         * manager zadan uruchamiamy tylko i tylko wtedy gdy chcemy stworzyc
         * nowy filtr czyli w przypadku gdy ten nie istnieje lub gdy
         * otrzymalismy nowe dane.
         *
         *
         */
        /**
         * Sprawdzamy czy istnieje już taki filtr
         *
         */
        // Jeśli lista nie jest pusta
        boolean isFilterExist = false;
        if (!this.resultContainer.getTaskParameterList().isEmpty()) {
            for (TaskParametersProvider p : this.resultContainer.getTaskParameterList()) {
                try {
                    if (p.getQueryId().equals(searchQueryId)) {
                        isFilterExist = true;
                    }
                } catch (NullPointerException ex) {

                }

            }
        }

        /**
         * Jeśli filtr jest na liście
         */
        if (isFilterExist) {
            LOG.info("@@@@@@ Filtr juz istnieje");

            /**
             *
             * Sprawdzamy czy filtr się zmienił Jeśli są parametry w request to
             * filtr sie ma zmienić
             *
             * Filtr się zmienił, sprawdzamy tylko czy posiada wartosc parametru
             * search Jeśli posiada to usuwamy stary filtr, i dodajemy nowy;
             *
             * Jeśli wartość parametru "search" pusta to tworzymy nowy filtr;
             */
            if (!request.getParameterMap().isEmpty()) {

                /**
                 * Mamy dane do nowego filtra
                 */
                if (request.getParameter("search") != null) {
                    if (!request.getParameter("search").isEmpty()) {
                        LOG.info("mamy dane do nowego filtra");
                        /**
                         * 1.Usuwamy stary filtr 2.Tworzymy filtr na podstawie
                         * nowych danych 3.dodajemy filtr do listy 4. Wywołujemy
                         * zadanie pobrania nowego filtra
                         */
                        // 1. Usuwamy
                        for (int i = 0; i < this.resultContainer.getTaskParameterList().size(); i++) {
                            if (this.resultContainer.getTaskParameterList().get(i).getQueryId().equals(searchQueryId)) {
                                this.resultContainer.getTaskParameterList().remove(i);
                            }
                        }
                        //2. Tworzymy filtr.
                        taskParameters.setTaskType(SearchTaskType.TASK_TYPE_GET_FILTER_LIST);
                        // Ustawiamy id dla danych parametrów wyszukiwania
                        taskParameters.setQueryId(searchQueryId);
                        taskParameters.setApiKey(appOptions.getApiKey());
                        // tworzymy filtr na podstawie danych z formularza
                        taskParameters.setFilterOptionsArray(this.filterFactory.fabricateFiltersFormRequestData(request, searchQueryId));

                        //3.Dodajemy do listy w sesji - nasz parametr jest już skojażony z 
                        // konkretnym egzemplarzem filtra
                        this.resultContainer.getTaskParameterList().add(taskParameters);
                        this.taskManager2.createNewTask(taskParameters);

                        /**
                         *
                         * Brak danych do stworzenia filtra więc go resetujemy
                         */
                    } else {
                        LOG.info("brak danych do nowego filtra");
                        /**
                         * Resetujemy nasz filtr Usuwamy stary, dodajemy nowy,
                         * świerzy filtr Usuwamy też odpowiednia mape wartosci
                         * parametrów
                         */

                        // 1. Usuwamy
                        for (int i = 0; i < this.resultContainer.getTaskParameterList().size(); i++) {
                            if (this.resultContainer.getTaskParameterList().get(i).getQueryId().equals(searchQueryId)) {
                                this.resultContainer.getTaskParameterList().remove(i);
                            }
                        }

                        if (searchQueryId.equals("search_query_0")) {
                            this.resultContainer.getParamsMap0().clear();
                        } else if (searchQueryId.equals("search_query_1")) {
                            this.resultContainer.getParamsMap1().clear();
                        } else if (searchQueryId.equals("search_query_2")) {
                            this.resultContainer.getParamsMap2().clear();
                        } else if (searchQueryId.equals("search_query_3")) {
                            this.resultContainer.getParamsMap3().clear();
                        } else if (searchQueryId.equals("search_query_4")) {
                            this.resultContainer.getParamsMap4().clear();
                        }

                        taskParameters.setTaskType(SearchTaskType.TASK_TYPE_GET_FILTER_LIST);
                        // Ustawiamy id dla danych parametrów wyszukiwania
                        taskParameters.setQueryId(searchQueryId);
                        taskParameters.setApiKey(appOptions.getApiKey());
                        // tworzymy filtr na podstawie danych z formularza
                        taskParameters.setFilterOptionsArray(new ArrayOfFilteroptionstype());

                        // Dodajemy do listy w sesji - nasz parametr jest już skojażony z 
                        // konkretnym egzemplarzem filtra
                        this.resultContainer.getTaskParameterList().add(taskParameters);
                        this.taskManager2.createNewTask(taskParameters);
                    }
                }
                /**
                 * Filtr się nie zmienia bo lista parametrów jest pusta,
                 * zarządano tylko odpowiedniego filtra z sessji
                 */
            } else {
                LOG.info("filtr się nie zmienia");

                for (TaskParametersProvider p : this.resultContainer.getTaskParameterList()) {
                    if (p.getQueryId().equals(searchQueryId)) {
                        this.taskManager2.createNewTask(p);
                    }
                }
            }

            /**
             * Jeśli nie ma takiego filtra na liscie
             */
        } else {
            LOG.info("@@@@@@ Filtr NIE istnieje");
            taskParameters.setTaskType(SearchTaskType.TASK_TYPE_GET_FILTER_LIST);
            // Ustawiamy id dla danych parametrów wyszukiwania
            taskParameters.setQueryId(searchQueryId);
            //taskParameters.setApiKey(appOptions.getApiKey());
            taskParameters.setApiKey("41e7acc7");
            // tworzymy filtr na podstawie danych z formularza
            //taskParameters.setFilterOptionsArray(this.filterFactory.getFiltersFormRequestData(request, searchQueryId));

            // Dodajemy do listy w sesji - nasz parametr jest już skojażony z 
            // konkretnym egzemplarzem filtra
            this.resultContainer.getTaskParameterList().add(taskParameters);
            this.taskManager2.createNewTask(taskParameters);
        }

        LOG.info("Parametry: " + request.getParameterMap().keySet().toString());

        /**
         * Sprawdzamy listę wątków w pętli
         */
        LOG.info("czekamy w kontrolerze na zakonczenie...");
        LOG.info("Ilość watkow w kontrzolerze filter0: " + this.taskManager2.getThreadList().size());

        do {

            for (int i = 0; i < this.taskManager2.getThreadList().size(); i++) {
                SearchThreadBean thread = this.taskManager2.getThreadList().get(i);
                if (!thread.isAlive()) {
                    this.taskManager2.getThreadList().remove(i);

                    LOG.info("mamy martwy watek");
                }
            }
            LOG.info("Szukamy martwych wątków dopuki lista nie bedzie pusta");
            LOG.info("rozmiar listy watkow: " + this.taskManager2.getThreadList().size());
            Thread.sleep(1000);
            /**
             * Czekamy az lista bedzie pusta, czli wszystkie wątki z tego
             * requestu zostaną zakończone.
             */
        } while (!this.taskManager2.getThreadList().isEmpty());

        LOG.info("Zakonczono.");

        ModelAndView model = new ModelAndView("/filters");

//        for (String s : this.rejectedFiltersCleaner.getCleanRejectedList(searchQueryId)) {
//            LOG.info("Odrzucono filtr: " + s);
//
//        }
        model.addObject("taskResultList", this.resultContainer.getTaskResultList());

        if (searchQueryId.equals("search_query_0")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap0());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        } else if (searchQueryId.equals("search_query_1")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap1());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        } else if (searchQueryId.equals("search_query_2")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap2());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        } else if (searchQueryId.equals("search_query_3")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap3());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        } else if (searchQueryId.equals("search_query_4")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap4());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        }
        LOG.info("witaj w " + request.getParameter("queryId"));
        model.addObject("searchQueryId", searchQueryId);
        return model;
    }

    /**
     *
     * ################################# #################################
     * ###### GENERATOR 1 #################################
     * #################################
     *
     */
    @RequestMapping(value = "/filterGenerator1", method = RequestMethod.POST)
    public ModelAndView filterGenerator1(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
        this.resultContainer.setLastUsedFilterId(1);
        this.resultContainer.clearAllResults();
        TaskParametersProvider taskParameters = new TaskParametersProviderImpl();

        String searchQueryId = "search_query_1";

        /**
         * Musimy zapamiętać otrzymany filtr w sesji i kojarzyc go z konkretnym
         * filtrem w tym celu zapisujemy go do listy parametrów w result
         * container;
         *
         * 1. Musimy wiedzieć czy filtr istnieje czy nie Jeśli nie istnieje to
         * go tworzymy.
         *
         * Jeśli istnieje to sprawdzamy czy się zmienił Jeśli się nie zmienił, -
         * przyszedł request z pustym parametrem "search" to to zostawiamy stary
         * filtr
         *
         * Jeśli się zmienił to musimy stworzyć nowy filtr, usunąć stary filtr
         * posiadajace odpowiednie queryId i dodać nowy filtr
         *
         * manager zadan uruchamiamy wtedy i tylko wtedy gdy chcemy stworzyc
         * nowy filtr czyli w przypadku gdy ten nie istnieje lub gdy
         * otrzymalismy nowe dane.
         *
         *
         */
        /**
         * Sprawdzamy czy istnieje już taki filtr
         *
         */
        // Jeśli lista nie jest pusta
        boolean isFilterExist = false;
        if (!this.resultContainer.getTaskParameterList().isEmpty()) {
            for (TaskParametersProvider p : this.resultContainer.getTaskParameterList()) {
                try {
                    if (p.getQueryId().equals(searchQueryId)) {
                        isFilterExist = true;
                    }
                } catch (NullPointerException ex) {

                }
            }
        }

        /**
         * Jeśli filtr jest na liście
         */
        if (isFilterExist) {
            LOG.info("@@@@@@ Filtr juz istnieje");

            /**
             *
             * Sprawdzamy czy filtr się zmienił Jeśli są parametry w request to
             * filtr sie ma zmienić
             *
             * Filtr się zmienił, sprawdzamy tylko czy posiada wartosc parametru
             * search Jeśli posiada to usuwamy stary filtr, i dodajemy nowy;
             *
             * Jeśli wartość parametru "search" pusta to tworzymy nowy filtr;
             */
            if (!request.getParameterMap().isEmpty()) {

                /**
                 * Mamy dane do nowego filtra
                 */
                if (!request.getParameter("search").isEmpty()) {
                    LOG.info("mamy dane do nowego filtra");
                    /**
                     * 1.Usuwamy stary filtr 2.Tworzymy filtr na podstawie
                     * nowych danych 3.dodajemy filtr do listy 4. Wywołujemy
                     * zadanie pobrania nowego filtra
                     */
                    // 1. Usuwamy
                    for (int i = 0; i < this.resultContainer.getTaskParameterList().size(); i++) {
                        if (this.resultContainer.getTaskParameterList().get(i).getQueryId().equals(searchQueryId)) {
                            this.resultContainer.getTaskParameterList().remove(i);
                        }
                    }
                    //2. Tworzymy filtr.
                    taskParameters.setTaskType(SearchTaskType.TASK_TYPE_GET_FILTER_LIST);
                    // Ustawiamy id dla danych parametrów wyszukiwania
                    taskParameters.setQueryId(searchQueryId);
                    taskParameters.setApiKey(appOptions.getApiKey());
                    // tworzymy filtr na podstawie danych z formularza
                    taskParameters.setFilterOptionsArray(this.filterFactory.fabricateFiltersFormRequestData(request, searchQueryId));

                    //3.Dodajemy do listy w sesji - nasz parametr jest już skojażony z 
                    // konkretnym egzemplarzem filtra
                    this.resultContainer.getTaskParameterList().add(taskParameters);
                    this.taskManager2.createNewTask(taskParameters);

                    /**
                     *
                     * Brak danych do stworzenia filtra więc go resetujemy
                     */
                } else {
                    LOG.info("brak danych do nowego filtra");
                    /**
                     * Resetujemy nasz filtr Usuwamy stary, dodajemy nowy,
                     * świerzy filtr Usuwamy też odpowiednia mape wartosci
                     * parametrów
                     */

                    // 1. Usuwamy
                    for (int i = 0; i < this.resultContainer.getTaskParameterList().size(); i++) {
                        if (this.resultContainer.getTaskParameterList().get(i).getQueryId().equals(searchQueryId)) {
                            this.resultContainer.getTaskParameterList().remove(i);
                        }
                    }

                    if (searchQueryId.equals("search_query_0")) {
                        this.resultContainer.getParamsMap0().clear();
                    } else if (searchQueryId.equals("search_query_1")) {
                        this.resultContainer.getParamsMap1().clear();
                    } else if (searchQueryId.equals("search_query_2")) {
                        this.resultContainer.getParamsMap2().clear();
                    } else if (searchQueryId.equals("search_query_3")) {
                        this.resultContainer.getParamsMap3().clear();
                    } else if (searchQueryId.equals("search_query_4")) {
                        this.resultContainer.getParamsMap4().clear();
                    }

                    taskParameters.setTaskType(SearchTaskType.TASK_TYPE_GET_FILTER_LIST);
                    // Ustawiamy id dla danych parametrów wyszukiwania
                    taskParameters.setQueryId(searchQueryId);
                    taskParameters.setApiKey(appOptions.getApiKey());
                    // tworzymy filtr na podstawie danych z formularza
                    taskParameters.setFilterOptionsArray(new ArrayOfFilteroptionstype());

                    // Dodajemy do listy w sesji - nasz parametr jest już skojażony z 
                    // konkretnym egzemplarzem filtra
                    this.resultContainer.getTaskParameterList().add(taskParameters);
                    this.taskManager2.createNewTask(taskParameters);
                }
                /**
                 * Filtr się nie zmienia bo lista parametrów jest pusta,
                 * zarządano tylko odpowiedniego filtra z sessji
                 */
            } else {
                LOG.info("filtr się nie zmienia");

                for (TaskParametersProvider p : this.resultContainer.getTaskParameterList()) {
                    if (p.getQueryId().equals(searchQueryId)) {
                        this.taskManager2.createNewTask(p);
                    }
                }
            }

            /**
             * Jeśli nie ma takiego filtra na liscie
             */
        } else {
            LOG.info("@@@@@@ Filtr NIE istnieje");
            taskParameters.setTaskType(SearchTaskType.TASK_TYPE_GET_FILTER_LIST);
            // Ustawiamy id dla danych parametrów wyszukiwania
            taskParameters.setQueryId(searchQueryId);
            taskParameters.setApiKey(appOptions.getApiKey());
            // tworzymy filtr na podstawie danych z formularza
            //taskParameters.setFilterOptionsArray(this.filterFactory.getFiltersFormRequestData(request, searchQueryId));

            // Dodajemy do listy w sesji - nasz parametr jest już skojażony z 
            // konkretnym egzemplarzem filtra
            this.resultContainer.getTaskParameterList().add(taskParameters);
            this.taskManager2.createNewTask(taskParameters);
        }

        LOG.info("Parametry: " + request.getParameterMap().keySet().toString());

        /**
         * Sprawdzamy listę wątków w pętli
         */
        LOG.info("czekamy w kontrolerze na zakonczenie...");
        LOG.info("Ilość watkow w kontrzolerze filter1: " + this.taskManager2.getThreadList().size());

//         for(SearchThreadBean t :this.taskManager2.getThreadList() ){
//             for(SearchOptType o : t.getParameters().getParameters()){
//                 LOG.info( "Szukana fraza !!!!! " +o.getSearchString());
//             }
//             
//         }
        do {

            for (int i = 0; i < this.taskManager2.getThreadList().size(); i++) {
                SearchThreadBean thread = this.taskManager2.getThreadList().get(i);

                if (!thread.isAlive()) {
                    this.taskManager2.getThreadList().remove(i);

                    LOG.info("mamy martwy watek");
                }
            }
            LOG.info("Szukamy martwych wątków dopuki lista nie bedzie pusta");
            LOG.info("rozmiar listy watkow: " + this.taskManager2.getThreadList().size());
            Thread.sleep(500);
            /**
             * Czekamy az lista bedzie pusta, czli wszystkie wątki z tego
             * requestu zostaną zakończone.
             */
        } while (!this.taskManager2.getThreadList().isEmpty());

        LOG.info("Zakonczono.");

        ModelAndView model = new ModelAndView("/filters");

//        for (String s : this.rejectedFiltersCleaner.getCleanRejectedList(searchQueryId)) {
//            LOG.info("Odrzucono filtr: " + s);
//
//        }
        model.addObject("taskResultList", this.resultContainer.getTaskResultList());

        if (searchQueryId.equals("search_query_0")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap0());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        } else if (searchQueryId.equals("search_query_1")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap1());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        } else if (searchQueryId.equals("search_query_2")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap2());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        } else if (searchQueryId.equals("search_query_3")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap3());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        } else if (searchQueryId.equals("search_query_4")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap4());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        }
        LOG.info("witaj w " + request.getParameter("queryId"));
        model.addObject("searchQueryId", searchQueryId);
        return model;
    }

    /**
     *
     * ################################# #################################
     * ###### GENERATOR 1 #################################
     * #################################
     *
     */
    @RequestMapping(value = "/filterGenerator2", method = RequestMethod.POST)
    public ModelAndView filterGenerator2(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
        this.resultContainer.setLastUsedFilterId(2);
        this.resultContainer.clearAllResults();
        TaskParametersProvider taskParameters = new TaskParametersProviderImpl();

        String searchQueryId = "search_query_2";

        /**
         * Musimy zapamiętać otrzymany filtr w sesji i kojarzyc go z konkretnym
         * filtrem w tym celu zapisujemy go do listy parametrów w result
         * container;
         *
         * 1. Musimy wiedzieć czy filtr istnieje czy nie Jeśli nie istnieje to
         * go tworzymy.
         *
         * Jeśli istnieje to sprawdzamy czy się zmienił Jeśli się nie zmienił, -
         * przyszedł request z pustym parametrem "search" to to zostawiamy stary
         * filtr
         *
         * Jeśli się zmienił to musimy stworzyć nowy filtr, usunąć stary filtr
         * posiadajace odpowiednie queryId i dodać nowy filtr
         *
         * manager zadan uruchamiamy tylko i tylko wtedy gdy chcemy stworzyc
         * nowy filtr czyli w przypadku gdy ten nie istnieje lub gdy
         * otrzymalismy nowe dane.
         *
         *
         */
        /**
         * Sprawdzamy czy istnieje już taki filtr
         *
         */
        // Jeśli lista nie jest pusta
        boolean isFilterExist = false;
        if (!this.resultContainer.getTaskParameterList().isEmpty()) {
            for (TaskParametersProvider p : this.resultContainer.getTaskParameterList()) {
                try {
                    if (p.getQueryId().equals(searchQueryId)) {
                        isFilterExist = true;
                    }
                } catch (NullPointerException ex) {

                }

            }
        }

        /**
         * Jeśli filtr jest na liście
         */
        if (isFilterExist) {
            LOG.info("@@@@@@ Filtr juz istnieje");

            /**
             *
             * Sprawdzamy czy filtr się zmienił Jeśli są parametry w request to
             * filtr sie ma zmienić
             *
             * Filtr się zmienił, sprawdzamy tylko czy posiada wartosc parametru
             * search Jeśli posiada to usuwamy stary filtr, i dodajemy nowy;
             *
             * Jeśli wartość parametru "search" pusta to tworzymy nowy filtr;
             */
            if (!request.getParameterMap().isEmpty()) {

                /**
                 * Mamy dane do nowego filtra
                 */
                if (!request.getParameter("search").isEmpty()) {
                    LOG.info("mamy dane do nowego filtra");
                    /**
                     * 1.Usuwamy stary filtr 2.Tworzymy filtr na podstawie
                     * nowych danych 3.dodajemy filtr do listy 4. Wywołujemy
                     * zadanie pobrania nowego filtra
                     */
                    // 1. Usuwamy
                    for (int i = 0; i < this.resultContainer.getTaskParameterList().size(); i++) {
                        if (this.resultContainer.getTaskParameterList().get(i).getQueryId().equals(searchQueryId)) {
                            this.resultContainer.getTaskParameterList().remove(i);
                        }
                    }
                    //2. Tworzymy filtr.
                    taskParameters.setTaskType(SearchTaskType.TASK_TYPE_GET_FILTER_LIST);
                    // Ustawiamy id dla danych parametrów wyszukiwania
                    taskParameters.setQueryId(searchQueryId);
                    taskParameters.setApiKey(appOptions.getApiKey());
                    // tworzymy filtr na podstawie danych z formularza
                    taskParameters.setFilterOptionsArray(this.filterFactory.fabricateFiltersFormRequestData(request, searchQueryId));

                    //3.Dodajemy do listy w sesji - nasz parametr jest już skojażony z 
                    // konkretnym egzemplarzem filtra
                    this.resultContainer.getTaskParameterList().add(taskParameters);
                    this.taskManager2.createNewTask(taskParameters);

                    /**
                     *
                     * Brak danych do stworzenia filtra więc go resetujemy
                     */
                } else {
                    LOG.info("brak danych do nowego filtra");
                    /**
                     * Resetujemy nasz filtr Usuwamy stary, dodajemy nowy,
                     * świerzy filtr Usuwamy też odpowiednia mape wartosci
                     * parametrów
                     */

                    // 1. Usuwamy
                    for (int i = 0; i < this.resultContainer.getTaskParameterList().size(); i++) {
                        if (this.resultContainer.getTaskParameterList().get(i).getQueryId().equals(searchQueryId)) {
                            this.resultContainer.getTaskParameterList().remove(i);
                        }
                    }

                    if (searchQueryId.equals("search_query_0")) {
                        this.resultContainer.getParamsMap0().clear();
                    } else if (searchQueryId.equals("search_query_1")) {
                        this.resultContainer.getParamsMap1().clear();
                    } else if (searchQueryId.equals("search_query_2")) {
                        this.resultContainer.getParamsMap2().clear();
                    } else if (searchQueryId.equals("search_query_3")) {
                        this.resultContainer.getParamsMap3().clear();
                    } else if (searchQueryId.equals("search_query_4")) {
                        this.resultContainer.getParamsMap4().clear();
                    }

                    taskParameters.setTaskType(SearchTaskType.TASK_TYPE_GET_FILTER_LIST);
                    // Ustawiamy id dla danych parametrów wyszukiwania
                    taskParameters.setQueryId(searchQueryId);
                    taskParameters.setApiKey(appOptions.getApiKey());
                    // tworzymy filtr na podstawie danych z formularza
                    taskParameters.setFilterOptionsArray(new ArrayOfFilteroptionstype());

                    // Dodajemy do listy w sesji - nasz parametr jest już skojażony z 
                    // konkretnym egzemplarzem filtra
                    this.resultContainer.getTaskParameterList().add(taskParameters);
                    this.taskManager2.createNewTask(taskParameters);
                }
                /**
                 * Filtr się nie zmienia bo lista parametrów jest pusta,
                 * zarządano tylko odpowiedniego filtra z sessji
                 */
            } else {
                LOG.info("filtr się nie zmienia");

                for (TaskParametersProvider p : this.resultContainer.getTaskParameterList()) {
                    if (p.getQueryId().equals(searchQueryId)) {
                        this.taskManager2.createNewTask(p);
                    }
                }
            }

            /**
             * Jeśli nie ma takiego filtra na liscie
             */
        } else {
            LOG.info("@@@@@@ Filtr NIE istnieje");
            taskParameters.setTaskType(SearchTaskType.TASK_TYPE_GET_FILTER_LIST);
            // Ustawiamy id dla danych parametrów wyszukiwania
            taskParameters.setQueryId(searchQueryId);
            taskParameters.setApiKey(appOptions.getApiKey());
            // tworzymy filtr na podstawie danych z formularza
            //taskParameters.setFilterOptionsArray(this.filterFactory.getFiltersFormRequestData(request, searchQueryId));

            // Dodajemy do listy w sesji - nasz parametr jest już skojażony z 
            // konkretnym egzemplarzem filtra
            this.resultContainer.getTaskParameterList().add(taskParameters);
            this.taskManager2.createNewTask(taskParameters);
        }

        LOG.info("Parametry: " + request.getParameterMap().toString());

        /**
         * Sprawdzamy listę wątków w pętli
         */
        LOG.info("czekamy w kontrolerze na zakonczenie...");
        do {

            for (int i = 0; i < this.taskManager2.getThreadList().size(); i++) {
                SearchThreadBean thread = this.taskManager2.getThreadList().get(i);
                if (!thread.isAlive()) {
                    this.taskManager2.getThreadList().remove(i);

                    LOG.info("mamy martwy watek");
                }
            }
            LOG.info("Szukamy martwych wątków dopuki lista nie bedzie pusta");
            LOG.info("rozmiar listy watkow: " + this.taskManager2.getThreadList().size());
            Thread.sleep(1000);
            /**
             * Czekamy az lista bedzie pusta, czli wszystkie wątki z tego
             * requestu zostaną zakończone.
             */
        } while (!this.taskManager2.getThreadList().isEmpty());

        LOG.info("Zakonczono.");

        ModelAndView model = new ModelAndView("/filters");

//        for (String s : this.rejectedFiltersCleaner.getCleanRejectedList(searchQueryId)) {
//            LOG.info("Odrzucono filtr: " + s);
//
//        }
        model.addObject("taskResultList", this.resultContainer.getTaskResultList());

        if (searchQueryId.equals("search_query_0")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap0());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        } else if (searchQueryId.equals("search_query_1")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap1());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        } else if (searchQueryId.equals("search_query_2")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap2());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        } else if (searchQueryId.equals("search_query_3")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap3());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        } else if (searchQueryId.equals("search_query_4")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap4());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        }
        LOG.info("witaj w " + request.getParameter("queryId"));
        model.addObject("searchQueryId", searchQueryId);
        return model;
    }

    /**
     *
     * ################################# #################################
     * ###### GENERATOR 3 #################################
     * #################################
     *
     */
    @RequestMapping(value = "/filterGenerator3", method = RequestMethod.POST)
    public ModelAndView filterGenerator3(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
        this.resultContainer.setLastUsedFilterId(3);
        this.resultContainer.clearAllResults();
        TaskParametersProvider taskParameters = new TaskParametersProviderImpl();

        String searchQueryId = "search_query_3";

        /**
         * Musimy zapamiętać otrzymany filtr w sesji i kojarzyc go z konkretnym
         * filtrem w tym celu zapisujemy go do listy parametrów w result
         * container;
         *
         * 1. Musimy wiedzieć czy filtr istnieje czy nie Jeśli nie istnieje to
         * go tworzymy.
         *
         * Jeśli istnieje to sprawdzamy czy się zmienił Jeśli się nie zmienił, -
         * przyszedł request z pustym parametrem "search" to to zostawiamy stary
         * filtr
         *
         * Jeśli się zmienił to musimy stworzyć nowy filtr, usunąć stary filtr
         * posiadajace odpowiednie queryId i dodać nowy filtr
         *
         * manager zadan uruchamiamy tylko i tylko wtedy gdy chcemy stworzyc
         * nowy filtr czyli w przypadku gdy ten nie istnieje lub gdy
         * otrzymalismy nowe dane.
         *
         *
         */
        /**
         * Sprawdzamy czy istnieje już taki filtr
         *
         */
        // Jeśli lista nie jest pusta
        boolean isFilterExist = false;
        if (!this.resultContainer.getTaskParameterList().isEmpty()) {
            for (TaskParametersProvider p : this.resultContainer.getTaskParameterList()) {
                try {
                    if (p.getQueryId().equals(searchQueryId)) {
                        isFilterExist = true;
                    }
                } catch (NullPointerException ex) {

                }

            }
        }

        /**
         * Jeśli filtr jest na liście
         */
        if (isFilterExist) {
            LOG.info("@@@@@@ Filtr juz istnieje");

            /**
             *
             * Sprawdzamy czy filtr się zmienił Jeśli są parametry w request to
             * filtr sie ma zmienić
             *
             * Filtr się zmienił, sprawdzamy tylko czy posiada wartosc parametru
             * search Jeśli posiada to usuwamy stary filtr, i dodajemy nowy;
             *
             * Jeśli wartość parametru "search" pusta to tworzymy nowy filtr;
             */
            if (!request.getParameterMap().isEmpty()) {

                /**
                 * Mamy dane do nowego filtra
                 */
                if (!request.getParameter("search").isEmpty()) {
                    LOG.info("mamy dane do nowego filtra");
                    /**
                     * 1.Usuwamy stary filtr 2.Tworzymy filtr na podstawie
                     * nowych danych 3.dodajemy filtr do listy 4. Wywołujemy
                     * zadanie pobrania nowego filtra
                     */
                    // 1. Usuwamy
                    for (int i = 0; i < this.resultContainer.getTaskParameterList().size(); i++) {
                        if (this.resultContainer.getTaskParameterList().get(i).getQueryId().equals(searchQueryId)) {
                            this.resultContainer.getTaskParameterList().remove(i);
                        }
                    }
                    //2. Tworzymy filtr.
                    taskParameters.setTaskType(SearchTaskType.TASK_TYPE_GET_FILTER_LIST);
                    // Ustawiamy id dla danych parametrów wyszukiwania
                    taskParameters.setQueryId(searchQueryId);
                    taskParameters.setApiKey(appOptions.getApiKey());
                    // tworzymy filtr na podstawie danych z formularza
                    taskParameters.setFilterOptionsArray(this.filterFactory.fabricateFiltersFormRequestData(request, searchQueryId));

                    //3.Dodajemy do listy w sesji - nasz parametr jest już skojażony z 
                    // konkretnym egzemplarzem filtra
                    this.resultContainer.getTaskParameterList().add(taskParameters);
                    this.taskManager2.createNewTask(taskParameters);

                    /**
                     *
                     * Brak danych do stworzenia filtra więc go resetujemy
                     */
                } else {
                    LOG.info("brak danych do nowego filtra");
                    /**
                     * Resetujemy nasz filtr Usuwamy stary, dodajemy nowy,
                     * świerzy filtr Usuwamy też odpowiednia mape wartosci
                     * parametrów
                     */

                    // 1. Usuwamy
                    for (int i = 0; i < this.resultContainer.getTaskParameterList().size(); i++) {
                        if (this.resultContainer.getTaskParameterList().get(i).getQueryId().equals(searchQueryId)) {
                            this.resultContainer.getTaskParameterList().remove(i);
                        }
                    }

                    if (searchQueryId.equals("search_query_0")) {
                        this.resultContainer.getParamsMap0().clear();
                    } else if (searchQueryId.equals("search_query_1")) {
                        this.resultContainer.getParamsMap1().clear();
                    } else if (searchQueryId.equals("search_query_2")) {
                        this.resultContainer.getParamsMap2().clear();
                    } else if (searchQueryId.equals("search_query_3")) {
                        this.resultContainer.getParamsMap3().clear();
                    } else if (searchQueryId.equals("search_query_4")) {
                        this.resultContainer.getParamsMap4().clear();
                    }

                    taskParameters.setTaskType(SearchTaskType.TASK_TYPE_GET_FILTER_LIST);
                    // Ustawiamy id dla danych parametrów wyszukiwania
                    taskParameters.setQueryId(searchQueryId);
                    taskParameters.setApiKey(appOptions.getApiKey());
                    // tworzymy filtr na podstawie danych z formularza
                    taskParameters.setFilterOptionsArray(new ArrayOfFilteroptionstype());

                    // Dodajemy do listy w sesji - nasz parametr jest już skojażony z 
                    // konkretnym egzemplarzem filtra
                    this.resultContainer.getTaskParameterList().add(taskParameters);
                    this.taskManager2.createNewTask(taskParameters);
                }
                /**
                 * Filtr się nie zmienia bo lista parametrów jest pusta,
                 * zarządano tylko odpowiedniego filtra z sessji
                 */
            } else {
                LOG.info("filtr się nie zmienia");

                for (TaskParametersProvider p : this.resultContainer.getTaskParameterList()) {
                    if (p.getQueryId().equals(searchQueryId)) {
                        this.taskManager2.createNewTask(p);
                    }
                }
            }

            /**
             * Jeśli nie ma takiego filtra na liscie
             */
        } else {
            LOG.info("@@@@@@ Filtr NIE istnieje");
            taskParameters.setTaskType(SearchTaskType.TASK_TYPE_GET_FILTER_LIST);
            // Ustawiamy id dla danych parametrów wyszukiwania
            taskParameters.setQueryId(searchQueryId);
            taskParameters.setApiKey(appOptions.getApiKey());
            // tworzymy filtr na podstawie danych z formularza
            //taskParameters.setFilterOptionsArray(this.filterFactory.getFiltersFormRequestData(request, searchQueryId));

            // Dodajemy do listy w sesji - nasz parametr jest już skojażony z 
            // konkretnym egzemplarzem filtra
            this.resultContainer.getTaskParameterList().add(taskParameters);
            this.taskManager2.createNewTask(taskParameters);
        }

        LOG.info("Parametry: " + request.getParameterMap().toString());

        /**
         * Sprawdzamy listę wątków w pętli
         */
        LOG.info("czekamy w kontrolerze na zakonczenie...");
        do {

            for (int i = 0; i < this.taskManager2.getThreadList().size(); i++) {
                SearchThreadBean thread = this.taskManager2.getThreadList().get(i);
                if (!thread.isAlive()) {
                    this.taskManager2.getThreadList().remove(i);

                    LOG.info("mamy martwy watek");
                }
            }
            LOG.info("Szukamy martwych wątków dopuki lista nie bedzie pusta");
            LOG.info("rozmiar listy watkow: " + this.taskManager2.getThreadList().size());
            Thread.sleep(1000);
            /**
             * Czekamy az lista bedzie pusta, czli wszystkie wątki z tego
             * requestu zostaną zakończone.
             */
        } while (!this.taskManager2.getThreadList().isEmpty());

        LOG.info("Zakonczono.");

        ModelAndView model = new ModelAndView("/filters");

//        for (String s : this.rejectedFiltersCleaner.getCleanRejectedList(searchQueryId)) {
//            LOG.info("Odrzucono filtr: " + s);
//
//        }
        model.addObject("taskResultList", this.resultContainer.getTaskResultList());

        if (searchQueryId.equals("search_query_0")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap0());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        } else if (searchQueryId.equals("search_query_1")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap1());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        } else if (searchQueryId.equals("search_query_2")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap2());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        } else if (searchQueryId.equals("search_query_3")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap3());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        } else if (searchQueryId.equals("search_query_4")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap4());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        }
        LOG.info("witaj w " + request.getParameter("queryId"));
        model.addObject("searchQueryId", searchQueryId);
        return model;
    }

    /**
     *
     * ################################# #################################
     * ###### GENERATOR 4 #################################
     * #################################
     *
     */
    @RequestMapping(value = "/filterGenerator4", method = RequestMethod.POST)
    public ModelAndView filterGenerator4(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
        this.resultContainer.setLastUsedFilterId(4);
        this.resultContainer.clearAllResults();
        TaskParametersProvider taskParameters = new TaskParametersProviderImpl();

        String searchQueryId = "search_query_4";

        /**
         * Musimy zapamiętać otrzymany filtr w sesji i kojarzyc go z konkretnym
         * filtrem w tym celu zapisujemy go do listy parametrów w result
         * container;
         *
         * 1. Musimy wiedzieć czy filtr istnieje czy nie Jeśli nie istnieje to
         * go tworzymy.
         *
         * Jeśli istnieje to sprawdzamy czy się zmienił Jeśli się nie zmienił, -
         * przyszedł request z pustym parametrem "search" to to zostawiamy stary
         * filtr
         *
         * Jeśli się zmienił to musimy stworzyć nowy filtr, usunąć stary filtr
         * posiadajace odpowiednie queryId i dodać nowy filtr
         *
         * manager zadan uruchamiamy tylko i tylko wtedy gdy chcemy stworzyc
         * nowy filtr czyli w przypadku gdy ten nie istnieje lub gdy
         * otrzymalismy nowe dane.
         *
         *
         */
        /**
         * Sprawdzamy czy istnieje już taki filtr
         *
         */
        // Jeśli lista nie jest pusta
        boolean isFilterExist = false;
        if (!this.resultContainer.getTaskParameterList().isEmpty()) {
            for (TaskParametersProvider p : this.resultContainer.getTaskParameterList()) {
                try {
                    if (p.getQueryId().equals(searchQueryId)) {
                        isFilterExist = true;
                    }
                } catch (NullPointerException ex) {

                }

            }
        }

        /**
         * Jeśli filtr jest na liście
         */
        if (isFilterExist) {
            LOG.info("@@@@@@ Filtr juz istnieje");

            /**
             *
             * Sprawdzamy czy filtr się zmienił Jeśli są parametry w request to
             * filtr sie ma zmienić
             *
             * Filtr się zmienił, sprawdzamy tylko czy posiada wartosc parametru
             * search Jeśli posiada to usuwamy stary filtr, i dodajemy nowy;
             *
             * Jeśli wartość parametru "search" pusta to tworzymy nowy filtr;
             */
            if (!request.getParameterMap().isEmpty()) {

                /**
                 * Mamy dane do nowego filtra
                 */
                if (!request.getParameter("search").isEmpty()) {
                    LOG.info("mamy dane do nowego filtra");
                    /**
                     * 1.Usuwamy stary filtr 2.Tworzymy filtr na podstawie
                     * nowych danych 3.dodajemy filtr do listy 4. Wywołujemy
                     * zadanie pobrania nowego filtra
                     */
                    // 1. Usuwamy
                    for (int i = 0; i < this.resultContainer.getTaskParameterList().size(); i++) {
                        if (this.resultContainer.getTaskParameterList().get(i).getQueryId().equals(searchQueryId)) {
                            this.resultContainer.getTaskParameterList().remove(i);
                        }
                    }
                    //2. Tworzymy filtr.
                    taskParameters.setTaskType(SearchTaskType.TASK_TYPE_GET_FILTER_LIST);
                    // Ustawiamy id dla danych parametrów wyszukiwania
                    taskParameters.setQueryId(searchQueryId);
                    taskParameters.setApiKey(appOptions.getApiKey());
                    // tworzymy filtr na podstawie danych z formularza
                    taskParameters.setFilterOptionsArray(this.filterFactory.fabricateFiltersFormRequestData(request, searchQueryId));

                    //3.Dodajemy do listy w sesji - nasz parametr jest już skojażony z 
                    // konkretnym egzemplarzem filtra
                    this.resultContainer.getTaskParameterList().add(taskParameters);
                    this.taskManager2.createNewTask(taskParameters);

                    /**
                     *
                     * Brak danych do stworzenia filtra więc go resetujemy
                     */
                } else {
                    LOG.info("brak danych do nowego filtra");
                    /**
                     * Resetujemy nasz filtr Usuwamy stary, dodajemy nowy,
                     * świerzy filtr Usuwamy też odpowiednia mape wartosci
                     * parametrów
                     */

                    // 1. Usuwamy
                    for (int i = 0; i < this.resultContainer.getTaskParameterList().size(); i++) {
                        if (this.resultContainer.getTaskParameterList().get(i).getQueryId().equals(searchQueryId)) {
                            this.resultContainer.getTaskParameterList().remove(i);
                        }
                    }

                    if (searchQueryId.equals("search_query_0")) {
                        this.resultContainer.getParamsMap0().clear();
                    } else if (searchQueryId.equals("search_query_1")) {
                        this.resultContainer.getParamsMap1().clear();
                    } else if (searchQueryId.equals("search_query_2")) {
                        this.resultContainer.getParamsMap2().clear();
                    } else if (searchQueryId.equals("search_query_3")) {
                        this.resultContainer.getParamsMap3().clear();
                    } else if (searchQueryId.equals("search_query_4")) {
                        this.resultContainer.getParamsMap4().clear();
                    }

                    taskParameters.setTaskType(SearchTaskType.TASK_TYPE_GET_FILTER_LIST);
                    // Ustawiamy id dla danych parametrów wyszukiwania
                    taskParameters.setQueryId(searchQueryId);
                    taskParameters.setApiKey(appOptions.getApiKey());
                    // tworzymy filtr na podstawie danych z formularza
                    taskParameters.setFilterOptionsArray(new ArrayOfFilteroptionstype());

                    // Dodajemy do listy w sesji - nasz parametr jest już skojażony z 
                    // konkretnym egzemplarzem filtra
                    this.resultContainer.getTaskParameterList().add(taskParameters);
                    this.taskManager2.createNewTask(taskParameters);
                }
                /**
                 * Filtr się nie zmienia bo lista parametrów jest pusta,
                 * zarządano tylko odpowiedniego filtra z sessji
                 */
            } else {
                LOG.info("filtr się nie zmienia");

                for (TaskParametersProvider p : this.resultContainer.getTaskParameterList()) {
                    if (p.getQueryId().equals(searchQueryId)) {
                        this.taskManager2.createNewTask(p);
                    }
                }
            }

            /**
             * Jeśli nie ma takiego filtra na liscie
             */
        } else {
            LOG.info("@@@@@@ Filtr NIE istnieje");
            taskParameters.setTaskType(SearchTaskType.TASK_TYPE_GET_FILTER_LIST);
            // Ustawiamy id dla danych parametrów wyszukiwania
            taskParameters.setQueryId(searchQueryId);
            taskParameters.setApiKey(appOptions.getApiKey());
            // tworzymy filtr na podstawie danych z formularza
            //taskParameters.setFilterOptionsArray(this.filterFactory.getFiltersFormRequestData(request, searchQueryId));

            // Dodajemy do listy w sesji - nasz parametr jest już skojażony z 
            // konkretnym egzemplarzem filtra
            this.resultContainer.getTaskParameterList().add(taskParameters);
            this.taskManager2.createNewTask(taskParameters);
        }

        LOG.info("Parametry: " + request.getParameterMap().toString());

        /**
         * Sprawdzamy listę wątków w pętli
         */
        LOG.info("czekamy w kontrolerze na zakonczenie...");
        do {

            for (int i = 0; i < this.taskManager2.getThreadList().size(); i++) {
                SearchThreadBean thread = this.taskManager2.getThreadList().get(i);
                if (!thread.isAlive()) {
                    this.taskManager2.getThreadList().remove(i);

                    LOG.info("mamy martwy watek");
                }
            }
            LOG.info("Szukamy martwych wątków dopuki lista nie bedzie pusta");
            LOG.info("rozmiar listy watkow: " + this.taskManager2.getThreadList().size());
            Thread.sleep(1000);
            /**
             * Czekamy az lista bedzie pusta, czli wszystkie wątki z tego
             * requestu zostaną zakończone.
             */
        } while (!this.taskManager2.getThreadList().isEmpty());

        LOG.info("Zakonczono.");

        ModelAndView model = new ModelAndView("/filters");

//        for (String s : this.rejectedFiltersCleaner.getCleanRejectedList(searchQueryId)) {
//            LOG.info("Odrzucono filtr: " + s);
//
//        }
        model.addObject("taskResultList", this.resultContainer.getTaskResultList());

        if (searchQueryId.equals("search_query_0")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap0());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        } else if (searchQueryId.equals("search_query_1")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap1());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        } else if (searchQueryId.equals("search_query_2")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap2());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        } else if (searchQueryId.equals("search_query_3")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap3());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        } else if (searchQueryId.equals("search_query_4")) {
            model.addObject("lastRequestData", this.resultContainer.getParamsMap4());
            model.addObject("rejectedFilters", this.rejectedFiltersCleaner.getCleanRejectedList(this.resultContainer.getParamsMap0()));
        }
        LOG.info("witaj w " + request.getParameter("queryId"));
        model.addObject("searchQueryId", searchQueryId);
        return model;
    }

    @RequestMapping(value = "/filters")
    public ModelAndView doGetFilters(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
        ModelAndView model = new ModelAndView("/filters");
        model.addObject("taskResultList", this.resultContainer.getTaskResultList());

        return model;
    }

}
