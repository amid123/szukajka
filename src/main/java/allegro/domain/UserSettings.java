/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
@Entity
public class UserSettings implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String itemsOnOnePage;
    private String allegroLogin;
    private String allegroPassword;
    private boolean accountConnectedToAllegro;


    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "username")
    private User user;

    
    public UserSettings(){
        super();
        this.itemsOnOnePage = "10";
        this.accountConnectedToAllegro = false;
    }
    
    public UserSettings(User u){
        this.itemsOnOnePage = "10";
        this.accountConnectedToAllegro = false;
        this.user = u;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 53 * hash + (this.itemsOnOnePage != null ? this.itemsOnOnePage.hashCode() : 0);
        hash = 53 * hash + (this.allegroLogin != null ? this.allegroLogin.hashCode() : 0);
        hash = 53 * hash + (this.allegroPassword != null ? this.allegroPassword.hashCode() : 0);
        hash = 53 * hash + (this.accountConnectedToAllegro ? 1 : 0);
        hash = 53 * hash + (this.user != null ? this.user.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserSettings other = (UserSettings) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.accountConnectedToAllegro != other.accountConnectedToAllegro) {
            return false;
        }
        if ((this.itemsOnOnePage == null) ? (other.itemsOnOnePage != null) : !this.itemsOnOnePage.equals(other.itemsOnOnePage)) {
            return false;
        }
        if ((this.allegroLogin == null) ? (other.allegroLogin != null) : !this.allegroLogin.equals(other.allegroLogin)) {
            return false;
        }
        if ((this.allegroPassword == null) ? (other.allegroPassword != null) : !this.allegroPassword.equals(other.allegroPassword)) {
            return false;
        }
        if (this.user != other.user && (this.user == null || !this.user.equals(other.user))) {
            return false;
        }
        return true;
    }
    
    

}
