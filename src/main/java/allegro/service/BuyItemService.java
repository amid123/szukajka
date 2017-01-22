/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.service;

import https.webapi_allegro_pl.service.DoBidItemRequest;
import https.webapi_allegro_pl.service.DoBidItemResponse;
import java.io.Serializable;
import java.util.logging.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
@Service
@Scope("request")
public class BuyItemService implements Serializable{

    public String bidItem(String sessionId, long offerId, long quantity, float price) {
        DoBidItemRequest bidRequest = new DoBidItemRequest();

        bidRequest.setSessionHandle(sessionId);
        bidRequest.setBidItId(offerId);
        bidRequest.setBidQuantity(quantity);
        LOG.info("Cena: " + price);
        if (price < 2) {
            bidRequest.setBidUserPrice(price);
        }else{
            bidRequest.setBidUserPrice((float)0);
        }

        bidRequest.setBidBuyNow((long) 1);

        DoBidItemResponse bidResponse = doBidItem(bidRequest);

        return bidResponse.getBidPrice();
    }
    private static final Logger LOG = Logger.getLogger(BuyItemService.class.getName());

    private static DoBidItemResponse doBidItem(https.webapi_allegro_pl.service.DoBidItemRequest parameters) {
        https.webapi_allegro_pl.service.ServiceService service = new https.webapi_allegro_pl.service.ServiceService();
        https.webapi_allegro_pl.service.ServicePort port = service.getServicePort();
        return port.doBidItem(parameters);
    }
}
