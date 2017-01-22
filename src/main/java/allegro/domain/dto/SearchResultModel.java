/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.domain.dto;

/**
 *
 * @author ringo99
 */
public class SearchResultModel {
    
    private String itemName;
    private String itemLocationCity;
    private String itemPrice;
    private String itemAuctionNumber;
    private String itemSellerName;

    @Override
    public String toString() {
        return "SearchResultModel{" + "itemName=" + itemName + ", itemLocationCity=" + itemLocationCity + ", itemPrice=" + itemPrice + ", itemAuctionNumber=" + itemAuctionNumber + ", itemSellerName=" + itemSellerName + '}';
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemLocationCity() {
        return itemLocationCity;
    }

    public void setItemLocationCity(String itemLocationCity) {
        this.itemLocationCity = itemLocationCity;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemAuctionNumber() {
        return itemAuctionNumber;
    }

    public void setItemAuctionNumber(String itemAuctionNumber) {
        this.itemAuctionNumber = itemAuctionNumber;
    }

    public String getItemSellerName() {
        return itemSellerName;
    }

    public void setItemSellerName(String itemSellerName) {
        this.itemSellerName = itemSellerName;
    }
    
}
