/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.domain.repositories;

import allegro.domain.User;
import allegro.domain.UserSettings;
import allegro.domain.UserRole;
import allegro.domain.VerificationToken;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
public interface UserRepository {
 
	User findByUserName(String username);
        void addNewUser(User user);
        void addNewUserRole(UserRole userRole);
        void addNewVerificationToken(VerificationToken token);
        VerificationToken findToken(String token);
        int enableUserAccount(String username);
        void addNewUserSettings(UserSettings options);
        void updateUserSettings(UserSettings options);
        UserSettings findUserSettings(String userName);
 
}