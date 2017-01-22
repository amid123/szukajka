/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.controllers;

import https.webapi_allegro_pl.service.SortOptionsType;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import allegro.service.threads.SearchThreadBean;
import allegro.service.FilterFactoryService;
import allegro.service.OnePageResultContainer;
import allegro.service.ResultContaioner;
import allegro.service.ResultGeneratorService;
import allegro.service.tasks.SearchTaskType;
import allegro.service.UserSettingsService;
import allegro.service.tasks.TaskManager;
import allegro.service.tasks.TaskParametersProvider;
import allegro.service.tasks.impl.TaskParametersProviderImpl;
import allegro.tools.AllegroLinkGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
@Controller
@Scope("request")
public class MainSearchController {
    
    private static final Logger LOG = Logger.getLogger(MainSearchController.class.getName());
    
    @Autowired
    FilterFactoryService filterFactory;
    @Autowired
    ResultContaioner resultContainer;
    @Autowired
    TaskManager taskManager;
    @Autowired
    AllegroLinkGenerator linkGenerator;
    @Autowired
    ResultGeneratorService resultListGenerator;
    @Autowired
    OnePageResultContainer page;
    @Autowired
    UserSettingsService userSettingsSetvice;

    /**
     *
     *
     * Obsługujemy stronę główna wyszukiwarki czyscimy listy - wejscie na tą
     * stronę wiąże się z rozpoczęciem nowego wyszukiwania
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/search")
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
        
        ModelAndView model = new ModelAndView("/search");
        this.resultContainer.getTaskParameterList().clear();
        this.resultContainer.getParamsMap0().clear();
        this.resultContainer.getParamsMap1().clear();
        this.resultContainer.getParamsMap2().clear();
        this.resultContainer.getParamsMap3().clear();
        this.resultContainer.getParamsMap4().clear();
        this.resultContainer.getTaskResultList().clear();
        
        try {
            
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String logedUserName = auth.getName(); //get logged in username
            model.addObject("logedUser", logedUserName);
        } catch (NullPointerException ex) {
            
            model.addObject("logedUser", "Brak danych");
        }
        
        return model;
    }

    /**
     *
     *
     * @return modelAndView
     * @throws InterruptedException
     */
    @RequestMapping("/dosearch")
    public ModelAndView doSearch(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
        ModelAndView model = new ModelAndView("/search");
        List<String> paramNames = Collections.list(request.getParameterNames());
        
        try {
            String logedUserName = new String();
            
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            logedUserName = auth.getName(); //get logged in username
            model.addObject("logedUser", logedUserName);
            model.addObject("connectionStatus", userSettingsSetvice.getUserSettings(logedUserName).isAccountConnectedToAllegro());
            
        } catch (NullPointerException ex) {
            model.addObject("logedUser", "Niezalogowany");
        }
        
        boolean hasPages = false;
        int pageNo = 1;
        for (String parameter : paramNames) {
            if (parameter.equals("page")) {
                hasPages = true;
                try {
                    pageNo = Integer.decode(request.getParameter(parameter));
                } catch (NumberFormatException ex) {
                    pageNo = 1;
                }
            }
        }

        //this.resultContainer.clearAllResults();
        // Pobieramy sobie nazwy parametrów które do nas przywędrowały 
        // z eteru :D
        /**
         *
         * Sprawdzamy w tym kontroleże listę parametrów request zawiera listę
         * "wyszukiwań" z głównej strony wyszukiwania, tam dodając filtr,
         * tworzymy nowe wyszukiwanie.
         *
         * Dla wszystkich wyszukiwań tworzymy zadania wyszukiwania w serwiesie
         * Allegro Przesłany parametr musi mieć wartość, inaczej jest to nie
         * zainicjowane wyszukiwanie
         *
         */
        /**
         *
         * Jeśli są jakies zadania
         */
        if (!hasPages) {
            if (!resultContainer.getTaskParameterList().isEmpty()) {
                //TaskParameters searchParameter
                for (TaskParametersProvider p : resultContainer.getTaskParameterList()) {
                    SortOptionsType sortType = new SortOptionsType();
                    TaskParametersProvider parameter = new TaskParametersProviderImpl((TaskParametersProviderImpl) p);

                    /**
                     * Dla konkretnego zadania sprawdzamy czy mielismy parametr
                     * srort_type i sort_dir i przekazujemy ich wartości do
                     * struktury SortOptionsType
                     */
                    try {
                        if (p.getQueryId().equals("search_query_0")) {
                            
                            if (this.resultContainer.getParamsMap0().containsKey("sort_type")) {
                                sortType.setSortType(this.resultContainer.getParamsMap0().get("sort_type").get(0));
                            }
                            if (this.resultContainer.getParamsMap0().containsKey("sort_dir")) {
                                sortType.setSortOrder(this.resultContainer.getParamsMap0().get("sort_dir").get(0));
                            }
                        } else if (p.getQueryId().equals("search_query_1")) {
                            if (this.resultContainer.getParamsMap1().containsKey("sort_type")) {
                                sortType.setSortType(this.resultContainer.getParamsMap1().get("sort_type").get(0));
                            }
                            if (this.resultContainer.getParamsMap1().containsKey("sort_dir")) {
                                sortType.setSortOrder(this.resultContainer.getParamsMap1().get("sort_dir").get(0));
                            }
                        } else if (p.getQueryId().equals("search_query_2")) {
                            if (this.resultContainer.getParamsMap2().containsKey("sort_type")) {
                                sortType.setSortType(this.resultContainer.getParamsMap2().get("sort_type").get(0));
                            }
                            if (this.resultContainer.getParamsMap2().containsKey("sort_dir")) {
                                sortType.setSortOrder(this.resultContainer.getParamsMap2().get("sort_dir").get(0));
                            }
                        } else if (p.getQueryId().equals("search_query_3")) {
                            if (this.resultContainer.getParamsMap3().containsKey("sort_type")) {
                                sortType.setSortType(this.resultContainer.getParamsMap3().get("sort_type").get(0));
                            }
                            if (this.resultContainer.getParamsMap3().containsKey("sort_dir")) {
                                sortType.setSortOrder(this.resultContainer.getParamsMap3().get("sort_dir").get(0));
                            }
                        } else if (p.getQueryId().equals("search_query_4")) {
                            if (this.resultContainer.getParamsMap4().containsKey("sort_type")) {
                                sortType.setSortType(this.resultContainer.getParamsMap4().get("sort_type").get(0));
                            }
                            if (this.resultContainer.getParamsMap4().containsKey("sort_dir")) {
                                sortType.setSortOrder(this.resultContainer.getParamsMap4().get("sort_dir").get(0));
                            }
                        }
                        
                        parameter.setSortOptionsType(sortType);
                    } catch (NullPointerException ex) {
                        
                        LOG.info("NPE podczas inicjacji sortowania w kontrolerze głównym");
                    }
                    
                    parameter.setTaskType(SearchTaskType.TASK_TYPE_GET_ITEM_LIST_BY_FILTER);
                    parameter.setSearchScopeSelector(0);
                    parameter.setMaxResultsPerQuery(1000);
                    parameter.setSearchIndex(0);
                    
                    this.taskManager.setMaxRedefinedResp(3000);
                    this.taskManager.createNewTask(parameter);
                }
            }
            /**
             *
             * Czekamy aż wszystkie wątki zakończą wyszuiwać
             *
             */
            do {
                for (int i = 0; i < this.taskManager.getThreadList().size(); i++) {
                    SearchThreadBean thread = this.taskManager.getThreadList().get(i);
                    if (!thread.isAlive()) {
                        this.taskManager.getThreadList().remove(i);
                    }
                }
                Thread.sleep(300);
                /**
                 * Czekamy az lista bedzie pusta, czli wszystkie wątki z tego
                 * requestu zostaną zakończone.
                 */
            } while (!this.taskManager.getThreadList().isEmpty());

            /**
             * poproszono o stronę; wiec przekazujemy liste do sortowania i
             * konkretną mapę
             */
            try {
                resultListGenerator.generatePages();
                if (page.getMapList().size() > 0) {
                    model.addObject("sellersList", this.page.getBestSellersList());
                    model.addObject("pageMap", this.page.getMapList().get(0));
                    model.addObject("pagesCount", this.page.getMapList().size());
                    model.addObject("currentPageNo", pageNo);
                }
            } catch (IndexOutOfBoundsException ex) {
                model.setViewName("/public/404");
            }
            
        } else {
            
            try {
                //resultListGenerator.generatePages();
                model.addObject("sellersList", this.page.getBestSellersList());
                if (this.page.getMapList().size() >= pageNo - 2) {
                    model.addObject("pageMap", this.page.getMapList().get(pageNo - 1));
                } else {
                    model.addObject("pageMap", this.page.getMapList().get(0));
                }
                
                model.addObject("pagesCount", this.page.getMapList().size());
                model.addObject("currentPageNo", pageNo);
            } catch (IndexOutOfBoundsException ex) {
                model.setViewName("/public/404");
            }
            
        }
        /**
         * Nie możemy tych dwóch metod wywołać w odwrotnej kolejności
         */

        // Kupiujemy ref do rezultatów aby koszyk mogł sobie skopiować 
        this.resultContainer.setTaskResultListCopy(this.resultContainer.getTaskResultList());

        // model.addObject("itemsMapByTaskMap", resultListGenerator.getUsersItemMapByTaskMap());
        model.addObject("lastUsedFilter", this.resultContainer.getLastUsedFilterId());
        model.addObject("taskList", this.resultContainer.getTaskParameterList());
        model.addObject("resultList", this.resultContainer.getTaskResultList());
        model.addObject("linkGenerator", this.linkGenerator);
        
        return model;
    }
}
