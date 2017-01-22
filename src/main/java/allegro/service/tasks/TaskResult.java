/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.service.tasks;

import https.webapi_allegro_pl.service.ArrayOfFilteroptionstype;
import https.webapi_allegro_pl.service.ArrayOfFilterslisttype;
import https.webapi_allegro_pl.service.ArrayOfItemslisttype;
import https.webapi_allegro_pl.service.ArrayOfString;
import https.webapi_allegro_pl.service.CategoriesListType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author arek
 */
public interface TaskResult {

    public boolean isSearchDone();

    public void setSearchDone(boolean searchDone);

    public String getQueryId();

    public void setQueryId(String queryId);

    public CategoriesListType getCategoriesList();

    public void setCategoriesList(CategoriesListType categoriesList);

    public ArrayOfFilterslisttype getArrayOfFilters();

    public void setArrayOfFilters(ArrayOfFilterslisttype arrayOfFilters);

    public ArrayOfString getRejectedFilters();

    public void setRejectedFilters(ArrayOfString rejectedFilters);

    public List<ArrayOfItemslisttype> getArrayOfItemsList();

    public void setArrayOfItemsList(List<ArrayOfItemslisttype> arrayOfItemsList);

    public int getItemsCount();

    public void setItemsCount(int itemsCount);

    public int getItemsFeaturedCount();

    public void setItemsFeaturedCount(int itemsFeaturedCount);

    public ArrayOfFilteroptionstype getArrayOfFiltersRequested();

    public void setArrayOfFiltersRequested(ArrayOfFilteroptionstype arrayOfFiltersRequested);

}
