/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.service.tasks.impl;

import allegro.service.tasks.TaskResult;
import https.webapi_allegro_pl.service.ArrayOfFilteroptionstype;
import https.webapi_allegro_pl.service.ArrayOfFilterslisttype;
import https.webapi_allegro_pl.service.ArrayOfItemslisttype;
import https.webapi_allegro_pl.service.ArrayOfString;
import https.webapi_allegro_pl.service.CategoriesListType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
@Component
@Scope("prototype")
public class TaskResultImpl implements Serializable, TaskResult {

    private CategoriesListType categoriesList;
    private ArrayOfFilterslisttype arrayOfFilters;
    private ArrayOfString rejectedFilters;
    private List<ArrayOfItemslisttype> arrayOfItemsList;
    // Przechowujemy tu informacje natemat requestu którego dotyczy odpoweidź
    private ArrayOfFilteroptionstype arrayOfFiltersRequested;

    private String queryId;

    private boolean searchDone;

    public boolean isSearchDone() {
        return searchDone;
    }

    public void setSearchDone(boolean searchDone) {
        this.searchDone = searchDone;
    }

    // Ilość pasujących odpowiedzi
    private int itemsCount;
    // Ilość promowanych odpowiedzi 
    private int itemsFeaturedCount;

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public TaskResultImpl() {
        this.searchDone = false;
        this.arrayOfFiltersRequested = new ArrayOfFilteroptionstype();
        this.arrayOfItemsList = new ArrayList();
        this.rejectedFilters = new ArrayOfString();
        this.arrayOfFilters = new ArrayOfFilterslisttype();
        this.categoriesList = new CategoriesListType();
    }

    public CategoriesListType getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(CategoriesListType categoriesList) {
        this.categoriesList = categoriesList;
    }

    public ArrayOfFilterslisttype getArrayOfFilters() {
        return arrayOfFilters;
    }

    public void setArrayOfFilters(ArrayOfFilterslisttype arrayOfFilters) {
        this.arrayOfFilters = arrayOfFilters;
    }

    public ArrayOfString getRejectedFilters() {
        return rejectedFilters;
    }

    public void setRejectedFilters(ArrayOfString rejectedFilters) {
        this.rejectedFilters = rejectedFilters;
    }

    public List<ArrayOfItemslisttype> getArrayOfItemsList() {
        if (this.arrayOfItemsList == null) {
            this.arrayOfItemsList = new ArrayList<ArrayOfItemslisttype>();
        }
        return arrayOfItemsList;
    }

    public void setArrayOfItemsList(List<ArrayOfItemslisttype> arrayOfItemsList) {
        this.arrayOfItemsList = arrayOfItemsList;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }

    public int getItemsFeaturedCount() {
        return itemsFeaturedCount;
    }

    public void setItemsFeaturedCount(int itemsFeaturedCount) {
        this.itemsFeaturedCount = itemsFeaturedCount;
    }

    public ArrayOfFilteroptionstype getArrayOfFiltersRequested() {
        return arrayOfFiltersRequested;
    }

    public void setArrayOfFiltersRequested(ArrayOfFilteroptionstype arrayOfFiltersRequested) {
        this.arrayOfFiltersRequested = arrayOfFiltersRequested;
    }

}
