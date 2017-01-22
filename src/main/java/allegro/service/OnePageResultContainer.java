/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.service;

import https.webapi_allegro_pl.service.ItemsListType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
@Service
@Scope("session")
public class OnePageResultContainer implements Serializable{

    private List< Map<String, Map< String, List<ItemsListType>>>> mapList;
    private List<String> bestSellersList;

    public OnePageResultContainer() {
        this.bestSellersList = new ArrayList();
        this.mapList = new ArrayList();
    }

    public List<Map<String, Map<String, List<ItemsListType>>>> getMapList() {
        return mapList;
    }

    public void setMapList(List<Map<String, Map<String, List<ItemsListType>>>> mapList) {
        this.mapList = mapList;
    }

    public List<String> getBestSellersList() {
        return bestSellersList;
    }

    public void setBestSellersList(List<String> bestSellersList) {
        this.bestSellersList = bestSellersList;
    }
}
