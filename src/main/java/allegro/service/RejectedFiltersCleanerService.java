/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.service;

import allegro.service.tasks.TaskResult;
import https.webapi_allegro_pl.service.FiltersListType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
@Service
@Scope("prototype")
public class RejectedFiltersCleanerService implements Serializable {

    @Autowired
    private ResultContaioner results;

    private List<String> allRejectedFiltersList;
    private List<String> cleanedRejectedFiltersList;
    private static final Logger LOG = Logger.getLogger(RejectedFiltersCleanerService.class.getName());

    public RejectedFiltersCleanerService() {
        this.cleanedRejectedFiltersList = new ArrayList();
        this.allRejectedFiltersList = new ArrayList();
    }

    /**
     * Collecting all rejected filters to one list and separate them of the
     * repeated filters in new list.
     * @param queryMap
     * @return 
     */
    public List<String> getCleanRejectedList(Map<String, List<String>> queryMap) {

        clearCommonFilterList();
        clearRejectedFilterList();
        rewriteRejectedFiltersToCommonList(queryMap);
        clearRepeatedFilters();

        return this.cleanedRejectedFiltersList;
    }

    private void clearRejectedFilterList() {
        this.cleanedRejectedFiltersList.clear();
    }

    private void clearCommonFilterList() {
        this.allRejectedFiltersList.clear();
    }

    /**
     * clear repeated filters rewrites it to newest list.
     */
    private void clearRepeatedFilters() {
        try {
            if (!this.allRejectedFiltersList.isEmpty() && this.allRejectedFiltersList != null) {

                // just iterate list
                for (String filter : this.allRejectedFiltersList) {
                    int instance = 0;

                    // if sorted list is empty then we need to add first element
                    if (this.cleanedRejectedFiltersList.isEmpty()) {
                        this.cleanedRejectedFiltersList.add(filter);
                    } else {
                        // if given filter exist on the list
                        for (String cleanFilter : this.cleanedRejectedFiltersList) {
                            if (cleanFilter.equals(filter)) {
                                instance++;
                            }
                        }
                        if (instance == 0) {
                            this.cleanedRejectedFiltersList.add(filter);
                        }
                    }
                }
            }

        } catch (NullPointerException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        }
    }

    /**
     * rewrite all rejected filters from all tasks to one common list.
     *
     * @param queryMap
     */
    private void rewriteRejectedFiltersToCommonList(Map<String, List<String>> queryMap) {
        /**
         * Lecimy przez wszystki filtry i przepisujemy nazwy odrzuconych filtrów
         * do jednej wspolnej listy sprawdzamy tylko filtry ktore były uzyte ;p
         */
        try {
            for (TaskResult task : this.results.getTaskResultList()) {
                try {
                    for (FiltersListType filter : task.getArrayOfFilters().getItem()) {
                        try {
                            if (queryMap.containsKey(filter.getFilterId())) {
                                if (!queryMap.get(filter.getFilterId()).isEmpty() && queryMap.get(filter.getFilterId()) != null) {
                                    for (String rejectedFilter : filter.getFilterRelations().getRelationExclude().getItem()) {
                                        if (rejectedFilter != null) {
                                            for (String val : queryMap.get(filter.getFilterId())) {
                                                if (!val.equals("") && !val.equals("not_selected")) {
                                                    this.allRejectedFiltersList.add(rejectedFilter);
                                                    LOG.info("Odrzucam filtr: " + rejectedFilter + " odrzucenie od filtra: " + filter.getFilterName());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (NullPointerException ex) {
                            LOG.log(Level.SEVERE, "There is no rejected filters");
                        }
                    }
                } catch (NullPointerException ex) {
                    LOG.log(Level.SEVERE, "There is no more filters in givn task!");
                }
            }
        } catch (NullPointerException ex) {
        }
    }

    public List<String> getAllRejectedFiltersList() {
        return allRejectedFiltersList;
    }

    public void setAllRejectedFiltersList(List<String> allRejectedFiltersList) {
        this.allRejectedFiltersList = allRejectedFiltersList;
    }

    public List<String> getCleanedRejectedFiltersList() {
        return cleanedRejectedFiltersList;
    }

    public void setCleanedRejectedFiltersList(List<String> cleanedRejectedFiltersList) {
        this.cleanedRejectedFiltersList = cleanedRejectedFiltersList;
    }
}
