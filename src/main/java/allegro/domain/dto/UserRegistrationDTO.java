/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.domain.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
//import javax.validation.constraints.;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
public class UserRegistrationDTO {

    @Pattern(regexp = "^[a-zA-ZąĄęĘżŻźŹóÓłŁćĆśŚńŃ0-9]{4,15}$", message = "Nazwa użytkownia może zawierać tylko male i duże litery, powinna się zawierać w 4-15 znaków")
    @NotEmpty
    private String username;

    @Pattern(regexp = "^[a-zA-ZąĄęĘżŻźŹóÓłŁćĆśŚńŃ0-9]{5,15}$", message = "Hasło może zawierać litery a-z, A-Z oraz cyfry. Minimum 5 znaków, maks 15 znaków")
    @NotEmpty
    private String password;
    
    @NotEmpty
    @Email
    private String email;
  


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

  

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
