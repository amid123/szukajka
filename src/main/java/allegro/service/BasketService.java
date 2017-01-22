/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.service;

import allegro.controllers.BasketController;
import allegro.domain.ItemInBasket;
import allegro.domain.UserSettings;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import allegro.service.options.ApplicationOptionsProvider;
import allegro.domain.repositories.UserRepository;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
@Service
@Scope("session")
public class BasketService implements Serializable {

    private static final Logger LOG = Logger.getLogger(BasketController.class.getName());
    private List<ItemInBasket> productList;
    private List<String> sellerList;
    Map<String, List<ItemInBasket>> itemsMap;
    

    String sessionId;

    @Autowired
    AllegroLoginService login;

    @Autowired
    BuyItemService bidItem;

    @Autowired
    UserRepository userDao;

    @Autowired
    ApplicationOptionsProvider appOptions;

    public ApplicationOptionsProvider getAppOptions() {
        return appOptions;
    }

    public Map<String, List<ItemInBasket>>  getItemsByUserMap() {

        
        List<String> sellers = new ArrayList();

        /**
         * Tworzymy czysta liste sprzedawcow
         */
        // Lecimy po glownej liscie
        for (ItemInBasket item : this.productList) {

            int sellerInstance = 0;
            // Jeśli nasza lista jest pusta dodajemy pierwszy element
            if (sellers.isEmpty()) {
                sellers.add(item.getSellerName());
            } else {

                for (String sellerName : sellers) {

                    if (item.getSellerName().equals(sellerName)) {
                        sellerInstance++;
                    }
                }
                if (sellerInstance == 0) {
                    sellers.add(item.getSellerName());
                }
            }
        }

        for (String userName : sellers) {
            /**
             * Lista produktów danego urzytkownika
             */
            List<ItemInBasket> userItems = new ArrayList();

            /**
             * przeszukujemy zadania i listy ich zwrotek, szukamy przedmiotów od
             * konkretnych sprzedajacych
             */
                for (ItemInBasket item : this.productList) {

                    /**
                     * Jeśli loginy sa rowne to to nasz prdzedmiot, dodajemy go
                     * do listy;
                     */
                    if (item.getSellerName().equals(userName)) {
                        userItems.add(item);
                    }
                }
            
            // Dodajemy naszą listę produktów do mapy i przypisujemy jej odpowiedni klucz
            itemsMap.put(userName, userItems);
        }

        for(String key : itemsMap.keySet()){
            for(ItemInBasket item :  itemsMap.get(key)){
                LOG.info("Sprzedawca: " + item.getSellerName() + " przedmiot: " + item.getProductTitle());
            }
        }

        return itemsMap;
    }

    public void cleanBasket() {
        this.sellerList.clear();
        this.productList.clear();
        this.itemsMap.clear();
    }

    public void setAppOptions(ApplicationOptionsProvider appOptions) {
        this.appOptions = appOptions;
    }

    public BasketService() {
        this.itemsMap = new HashMap();
        this.sellerList = new ArrayList();
        this.sessionId = new String();
        this.productList = new ArrayList();
    }

    public boolean buyItems() {

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String logedUserName = auth.getName(); //get logged in username

            UserSettings userOptions = this.userDao.findUserSettings(logedUserName);

            if (sessionId.isEmpty()) {
                this.sessionId = this.login.login(userOptions.getAllegroLogin(), userOptions.getAllegroPassword(), appOptions.getApiKey());
            }

            /**
             * Kupujemy wszystkie przedmioty pokolei;
             */
            for (ItemInBasket item : productList) {
                String res = this.bidItem.bidItem(sessionId, item.getProductId(), item.getQuantity(), item.getPriceBuyItNow());
                LOG.info("Kupuję przedmiot: " + item.getProductTitle() + " status: " + res);
            }
            return true;

        } catch (NullPointerException ex) {
            LOG.info("BasketBean:buyItems: NPE");
            return false;
        }
    }

    public String getPriceSumString() {

        float sumPrice = 0;
        if (!this.productList.isEmpty()) {
            for (ItemInBasket item : this.productList) {

                sumPrice = sumPrice + (item.getPriceWitchDelivery() * item.getQuantity());
            }

        } else {
            return Float.toString(sumPrice);
        }

        return Float.toString(sumPrice);
    }

    public List<ItemInBasket> getProductList() {
        return productList;
    }

    public void setProductList(List<ItemInBasket> productList) {
        this.productList = productList;
    }

    public List<String> getSellerList() {
        return sellerList;
    }

    public void setSellerList(List<String> sellerList) {
        this.sellerList = sellerList;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public AllegroLoginService getLogin() {
        return login;
    }

    public void setLogin(AllegroLoginService login) {
        this.login = login;
    }

    public BuyItemService getBidItem() {
        return bidItem;
    }

    public void setBidItem(BuyItemService bidItem) {
        this.bidItem = bidItem;
    }

    public UserRepository getUserDao() {
        return userDao;
    }

    public void setUserDao(UserRepository userDao) {
        this.userDao = userDao;
    }

    public Map<String, List<ItemInBasket>> getItemsMap() {
        return itemsMap;
    }

    public void setItemsMap(Map<String, List<ItemInBasket>> itemsMap) {
        this.itemsMap = itemsMap;
    }
    

}
