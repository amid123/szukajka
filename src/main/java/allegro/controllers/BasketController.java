/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.controllers;

import allegro.domain.ItemInBasket;
import allegro.service.BasketService;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class BasketController implements Serializable {

    private static final Logger LOG = Logger.getLogger(BasketController.class.getName());
    //private List<ItemInBasket> productList;

    @Autowired
    BasketService basket;

    @RequestMapping("/basketView")
    public ModelAndView basketView(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {

        ModelAndView model = new ModelAndView("/basket");
        model.addObject("productList", this.basket.getProductList());
        model.addObject("basket", this.basket);

        return model;
    }

    /**
     * Dodajemy produkt do listy na podstawie pobranego parametru - id;
     *
     * @param request
     * @param response
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/basketAddProduct")
    public ModelAndView basketAddProduct(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {

        ModelAndView model = new ModelAndView("/basket");

        List<String> paramNames = Collections.list(request.getParameterNames());

        ItemInBasket item = new ItemInBasket();

        boolean productExist = false;
        for (String parameter : paramNames) {
            if (parameter.equals("productId")) {
                long itemId = Long.decode(request.getParameter(parameter));

                for (ItemInBasket testItem : this.basket.getProductList()) {

                    if (testItem.getProductId() == itemId) {
                        productExist = true;
                    }
                }
            }
        }

        /**
         * Jeśli produkt jest na liscie to znajdujemy go i dodajemy mu quantity
         * +1;
         */
        if (productExist) {

            for (String parameter : paramNames) {
                if (parameter.equals("productId")) {
                    long itemId = Long.decode(request.getParameter(parameter));

                    for (ItemInBasket testItem : this.basket.getProductList()) {

                        if (testItem.getProductId() == itemId) {
                            testItem.setQuantity(testItem.getQuantity() + 1);
                        }
                    }
                }
            }

            // Jeśli nie ma takiego produktu na liscie to tworzymy nowy i dodajemy
        } else {
            for (String parameter : paramNames) {

                //productId: "${item.itemId}", productTitle: "${item.itemTitle}", imgUrl:"
                if (parameter.equals("productId")) {
            

                    item.setProductId(Long.decode(request.getParameter(parameter)));

                } else if (parameter.equals("productTitle")) {
               
                    item.setProductTitle(request.getParameter(parameter));

                } else if (parameter.equals("imgUrl")) {
                    item.setImgLink(request.getParameter(parameter));
                } else if (parameter.equals("sellerName")) {
                    item.setSellerName(request.getParameter(parameter) );
                }else if (parameter.equals("priceWithDelivery")) {
                    LOG.info("dodaje img");
                    item.setPriceWitchDelivery(Float.parseFloat(request.getParameter(parameter)));

                } else if (parameter.equals("priceBuyNow")) {
                    LOG.info("dodaje img");
                    item.setPriceBuyItNow(Float.parseFloat(request.getParameter(parameter)));

                }

            }
            item.setQuantity(1);
            this.basket.getProductList().add(item);
            
        }

        
        model.addObject("productList", this.basket.getProductList());

        
        return model;
    }

    @RequestMapping("/basketRemoveProduct")
    public ModelAndView basketRemoveProduct(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {

        ModelAndView model = new ModelAndView("/basket");

        List<String> paramNames = Collections.list(request.getParameterNames());

        ItemInBasket item = new ItemInBasket();

        long itemId = 0;
        for (String parameter : paramNames) {
            LOG.info("parametry: " + parameter);
            if (parameter.equals("remove")) {
                itemId = Long.decode(request.getParameter(parameter));
                LOG.info("Otrzymano produkt do usunięcia");
            }
        }

        if (itemId != 0) {

            for (int i = 0; i < this.basket.getProductList().size(); i++) {
                if (this.basket.getProductList().get(i).getProductId() == itemId) {

                    basket.getProductList().remove(i);
                    LOG.info("usuwam produkt z koszyka o id" + Integer.toString(i));
                }
            }

            for (ItemInBasket itemToRemove : this.basket.getProductList()) {
                if (itemToRemove.getProductId() == itemId) {

                    this.basket.getProductList().remove(itemToRemove);
                }
            }
        }
        return model;
    }

    @RequestMapping("/basketClear")
    public ModelAndView basketClear(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
        ModelAndView model = new ModelAndView("/basket");
        LOG.info("Wyczysciles liste");
        this.basket.cleanBasket();

        return model;
    }

    @RequestMapping("/basketBuyAll")
    public ModelAndView basketBuyAll(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {

        ModelAndView model = new ModelAndView("/basket");

        //basket.buyItems();
        LOG.info("Kupiles przedmioty");
        this.basket.cleanBasket();

        return model;
    }

    @RequestMapping("/basketp")
    public ModelAndView showBasketPage(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {

        ModelAndView model = new ModelAndView("/basketpage");

        try {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String logedUserName = auth.getName(); //get logged in username
            model.addObject("logedUser", logedUserName);
        } catch (NullPointerException ex) {
            LOG.info("MainController: Nie znaleziono urzytkownika NPE errot");
            model.addObject("logedUser", "Marian Kopytko");
        }
        

        if(this.basket.getProductList().isEmpty()){
            this.basket.getItemsByUserMap().clear();
        }
        
        
        model.addObject("basketMap", this.basket.getItemsByUserMap() );
        model.addObject("basket", this.basket);
        
        
        return model;
    }

}
