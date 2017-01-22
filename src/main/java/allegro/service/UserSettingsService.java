/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.service;

import allegro.domain.UserSettings;
import allegro.domain.dto.UserSettingsDTO;
import com.sun.xml.ws.fault.ServerSOAPFaultException;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import allegro.domain.repositories.UserRepository;
import java.util.logging.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
@Service
@Scope("prototype")
public class UserSettingsService {

    private static final Logger LOG = Logger.getLogger(UserSettingsService.class.getName());

    @Value("${app.settings.allegro.apikey}")
    String apiKey;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AllegroLoginService login;

    public UserSettings getLogedUserOptions() {

        try {
            String logedUserName = getLogedUserName();
            return this.findSettingsByUserName(logedUserName);

        } catch (NullPointerException ex) {
            LOG.log(Level.SEVERE, "Error in reading user settings by userName parameter. Settings do not exist.");
            return null;
        }
    }

    private String getLogedUserName() {
        Authentication authentication = getAuthentication();
        String logedUserName = authentication.getName();
        return logedUserName;
    }

    private Authentication getAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth;
    }

    public UserSettingsDTO getUserSettings(String username) {
        UserSettingsDTO settingsDto = new UserSettingsDTO();
        UserSettings settings = findSettingsByUserName(username);
        return rewriteSettingsToDto(settingsDto, settings);
    }

    private UserSettingsDTO rewriteSettingsToDto(UserSettingsDTO settingsDto, UserSettings settings) {
        settingsDto.setAccountConnectedToAllegro(settings.isAccountConnectedToAllegro());
        settingsDto.setAllegroLogin(settings.getAllegroLogin());
        settingsDto.setAllegroPassword(settings.getAllegroPassword());
        settingsDto.setItemsOnOnePage(settings.getItemsOnOnePage());
        return settingsDto;
    }

    private UserSettings findSettingsByUserName(String username) {
        UserSettings opt = userRepository.findUserSettings(username);
        return opt;
    }

    public void updateUserSettings(String username, UserSettingsDTO settingsDto) {
        UserSettings settings = createSettings(username, settingsDto);
        tryToConnectAccount(settings);
        updateSettings(settings);
    }

    private void updateSettings(UserSettings settings) {
        this.userRepository.updateUserSettings(settings);
    }

    private void tryToConnectAccount(UserSettings settings) {
        try {
            if (isAuthorizationDataCorrect(settings)) {
                settings.setAccountConnectedToAllegro(true);
            }
        } catch (ServerSOAPFaultException ex) {
            LOG.log(Level.SEVERE, "Error in account connecting to allegro service: ", ex);
            settings.setAccountConnectedToAllegro(false);
        }
    }

    private UserSettings createSettings(String username, UserSettingsDTO settingsDto) {
        UserSettings settings;
        settings = userRepository.findUserSettings(username);
        settings.setAllegroLogin(settingsDto.getAllegroLogin());
        if (!settingsDto.getAllegroPassword().isEmpty()) {
            settings.setAllegroPassword(settingsDto.getAllegroPassword());
        }
        settings.setItemsOnOnePage(settingsDto.getItemsOnOnePage());
        return settings;
    }

    private boolean isAuthorizationDataCorrect(UserSettings settings) {
        return this.login.login(settings.getAllegroLogin(), settings.getAllegroPassword(), apiKey) != null;
    }
}
