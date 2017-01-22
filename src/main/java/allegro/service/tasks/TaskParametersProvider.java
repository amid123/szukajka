/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.service.tasks;

import https.webapi_allegro_pl.service.ArrayOfFilteroptionstype;
import https.webapi_allegro_pl.service.SortOptionsType;

/**
 *
 * @author arek
 */
public interface TaskParametersProvider {

    public int getMaxResultsPerQuery();

    public void setMaxResultsPerQuery(int maxResultsPerQuery);

    public int getSearchIndex();

    public void setSearchIndex(int searchIndex);

    public int getSearchCountrySelector();

    public void setSearchCountrySelector(int searchCountrySelector);

    public int getSearchScopeSelector();

    public void setSearchScopeSelector(int searchScopeSelector);

    public String getApiKey();

    public void setApiKey(String apiKey);

    public String getQueryId();

    public void setQueryId(String queryId);

    public SortOptionsType getSortOptionsType();

    public void setSortOptionsType(SortOptionsType sortOptionsType);

    public ArrayOfFilteroptionstype getFilterOptionsArray();

    public void setFilterOptionsArray(ArrayOfFilteroptionstype filterOptionsArray);

    public String getTaskType();

    public void setTaskType(String taskType);
}
