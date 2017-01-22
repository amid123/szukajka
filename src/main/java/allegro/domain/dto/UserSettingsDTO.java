/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.domain.dto;

import javax.validation.constraints.Digits;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
public class UserSettingsDTO {

    @Digits(fraction = 3, integer = Integer.MAX_VALUE)
    private String itemsOnOnePage;
    private String allegroLogin;
    private String allegroPassword;
    private boolean accountConnectedToAllegro;

    public UserSettingsDTO() {
        this.allegroPassword = new String();
        this.allegroLogin = new String();
        this.accountConnectedToAllegro = false;
        this.itemsOnOnePage = "20";
    }

    public String getItemsOnOnePage() {
        return itemsOnOnePage;
    }

    public void setItemsOnOnePage(String itemsOnOnePage) {
        this.itemsOnOnePage = itemsOnOnePage;
    }

    public String getAllegroLogin() {
        return allegroLogin;
    }

    public void setAllegroLogin(String allegroLogin) {
        this.allegroLogin = allegroLogin;
    }

    public String getAllegroPassword() {
        return allegroPassword;
    }

    public void setAllegroPassword(String allegroPassword) {
        this.allegroPassword = allegroPassword;
    }

    public boolean isAccountConnectedToAllegro() {
        return accountConnectedToAllegro;
    }

    public void setAccountConnectedToAllegro(boolean accountConnectedToAllegro) {
        this.accountConnectedToAllegro = accountConnectedToAllegro;
    }

}
