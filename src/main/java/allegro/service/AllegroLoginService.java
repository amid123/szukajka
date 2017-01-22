/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.service;

import https.webapi_allegro_pl.service.DoLoginEncResponse;
import https.webapi_allegro_pl.service.DoLoginRequest;
import https.webapi_allegro_pl.service.DoLoginResponse;
import https.webapi_allegro_pl.service.DoQuerySysStatusRequest;
import https.webapi_allegro_pl.service.DoQuerySysStatusResponse;
import java.io.Serializable;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
@Service
public class AllegroLoginService implements Serializable {

    private static final Logger LOG = Logger.getLogger(AllegroLoginService.class.getName());

    DoQuerySysStatusResponse responseVersion;

    String sessionId;

    public DoQuerySysStatusResponse getResponseVersion() {
        return responseVersion;
    }

    public void setResponseVersion(DoQuerySysStatusResponse responseVersion) {
        this.responseVersion = responseVersion;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public AllegroLoginService() {
        this.responseVersion = new DoQuerySysStatusResponse();
    }

    public String login(String userName, String password, String apiKey) {

        DoQuerySysStatusRequest requestVersion = createSysStatusRequest(apiKey);
        responseVersion = doQuerySysStatus(requestVersion);
        DoLoginRequest loginRequest = createLoginRequest(apiKey, userName, password);

        DoLoginResponse responseLogin = doLogin(loginRequest);
        return responseLogin.getSessionHandlePart();
    }

    private DoQuerySysStatusRequest createSysStatusRequest(String apiKey) {
        DoQuerySysStatusRequest requestVersion = new DoQuerySysStatusRequest();
        requestVersion.setWebapiKey(apiKey);
        requestVersion.setCountryId(1);
        requestVersion.setSysvar(3);
        return requestVersion;
    }

    private DoLoginRequest createLoginRequest(String apiKey, String userName, String password) {
        // Tworzymy instancje zapytania doLogin
        DoLoginRequest loginRequest = new DoLoginRequest();
        loginRequest.setCountryCode(1); // Kod kraju
        loginRequest.setLocalVersion(responseVersion.getVerKey()); // Wersja api
        loginRequest.setWebapiKey(apiKey);
        loginRequest.setUserLogin(userName);
        loginRequest.setUserPassword(password);
        return loginRequest;
    }

    private static DoQuerySysStatusResponse doQuerySysStatus(https.webapi_allegro_pl.service.DoQuerySysStatusRequest parameters) {
        https.webapi_allegro_pl.service.ServiceService service = new https.webapi_allegro_pl.service.ServiceService();
        https.webapi_allegro_pl.service.ServicePort port = service.getServicePort();
        return port.doQuerySysStatus(parameters);
    }

    private static DoLoginEncResponse doLoginEnc(https.webapi_allegro_pl.service.DoLoginEncRequest parameters) {
        https.webapi_allegro_pl.service.ServiceService service = new https.webapi_allegro_pl.service.ServiceService();
        https.webapi_allegro_pl.service.ServicePort port = service.getServicePort();
        return port.doLoginEnc(parameters);
    }

    private static DoLoginResponse doLogin(https.webapi_allegro_pl.service.DoLoginRequest parameters) {
        https.webapi_allegro_pl.service.ServiceService service = new https.webapi_allegro_pl.service.ServiceService();
        https.webapi_allegro_pl.service.ServicePort port = service.getServicePort();
        return port.doLogin(parameters);
    }

}
