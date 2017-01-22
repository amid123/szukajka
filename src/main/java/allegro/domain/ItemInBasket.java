/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.domain;

import java.io.Serializable;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
public class ItemInBasket implements Serializable{
    private String sellerName;
    private String productTitle;
    private long productId;
    private String imgLink;
    private int quantity;
    private float priceWitchDelivery;
    private float priceBuyItNow;
    private float priceBid;

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    
    public float getPriceWitchDelivery() {
        return priceWitchDelivery;
    }

    public void setPriceWitchDelivery(float priceWitchDelivery) {
        this.priceWitchDelivery = priceWitchDelivery;
    }

    public float getPriceBuyItNow() {
        return priceBuyItNow;
    }

    public void setPriceBuyItNow(float priceBuyItNow) {
        this.priceBuyItNow = priceBuyItNow;
    }

    public float getPriceBid() {
        return priceBid;
    }

    public void setPriceBid(float priceBid) {
        this.priceBid = priceBid;
    }

    

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
