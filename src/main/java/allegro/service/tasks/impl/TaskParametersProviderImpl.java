package allegro.service.tasks.impl;

import allegro.service.tasks.SearchTaskType;
import allegro.service.tasks.TaskParametersProvider;
import https.webapi_allegro_pl.service.ArrayOfFilteroptionstype;
import https.webapi_allegro_pl.service.SortOptionsType;
import java.io.Serializable;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
// Przechowuje parametry przekazywane do zadania
// typ zadania i liste opcji wyszukiwania i liste sprzedawcow
public class TaskParametersProviderImpl implements Serializable, TaskParametersProvider {

    /**
     *
     * Konstruktor kopiujący
     *
     * @param p
     */
    public TaskParametersProviderImpl(TaskParametersProviderImpl p) {

        this.apiKey = new String(p.apiKey);

        this.queryId = new String(p.queryId);
        this.taskType = new String(p.taskType);

        this.filterOptionsArray = p.filterOptionsArray;
        this.sortOptionsType = p.sortOptionsType;

        this.maxResultsPerQuery = p.maxResultsPerQuery;
        this.searchIndex = p.searchIndex;
        this.searchCountrySelector = p.searchCountrySelector;
        this.searchScopeSelector = p.searchScopeSelector;
    }

    public TaskParametersProviderImpl() {
        this.searchScopeSelector = 0;
        this.searchCountrySelector = 1;
        this.searchIndex = 0;
        this.maxResultsPerQuery = 1000;

        this.taskType = SearchTaskType.TASK_TYPE_GET_FIRST_RESULT;
    }

    // Przechowujemy tutaj id wątku
    // w celu identyfikacji poszczergólnych wyszukiwań
    // w danej sesji
    private String queryId;

    private String taskType;

    SortOptionsType sortOptionsType;
    ArrayOfFilteroptionstype filterOptionsArray;

    private String apiKey;

    private int maxResultsPerQuery;
    private int searchIndex;
    private int searchCountrySelector;
    private int searchScopeSelector;

    public int getMaxResultsPerQuery() {
        return maxResultsPerQuery;
    }

    public void setMaxResultsPerQuery(int maxResultsPerQuery) {
        this.maxResultsPerQuery = maxResultsPerQuery;
    }

    public int getSearchIndex() {
        return searchIndex;
    }

    public void setSearchIndex(int searchIndex) {
        this.searchIndex = searchIndex;
    }

    public int getSearchCountrySelector() {
        return searchCountrySelector;
    }

    public void setSearchCountrySelector(int searchCountrySelector) {
        this.searchCountrySelector = searchCountrySelector;
    }

    public int getSearchScopeSelector() {
        return searchScopeSelector;
    }

    public void setSearchScopeSelector(int searchScopeSelector) {
        this.searchScopeSelector = searchScopeSelector;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public SortOptionsType getSortOptionsType() {
        return sortOptionsType;
    }

    public void setSortOptionsType(SortOptionsType sortOptionsType) {
        this.sortOptionsType = sortOptionsType;
    }

    public ArrayOfFilteroptionstype getFilterOptionsArray() {
        return filterOptionsArray;
    }

    public void setFilterOptionsArray(ArrayOfFilteroptionstype filterOptionsArray) {
        this.filterOptionsArray = filterOptionsArray;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

}
