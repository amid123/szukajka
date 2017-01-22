package allegro.service.registration;

import allegro.domain.User;
import allegro.domain.UserSettings;
import allegro.domain.UserRole;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import allegro.domain.repositories.UserRepository;

/**
 *
 * @author Arek
 */
@Service
@Scope("request")
public class UserRegister {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ActivationProcess activationProces;

    public void register(String login, String pwd, String email,
            String role) throws NoSuchAlgorithmException, UnsupportedEncodingException, MessagingException {

        User user = createUser(pwd, login, email);
        UserRole userRole = createUserRole(user, role);

        if (isUserExist(login)) {
            addUser(user, userRole);
            startActivation(user);
        }
        addUserSettings(user);
    }

    private UserRole createUserRole(User user, String role) {
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        return userRole;
    }

    private User createUser(String pwd, String login, String email) {
        User user = new User();
        user.setPassword(encodePassword(pwd));
        user.setEnabled(false);
        user.setUsername(login);
        user.setEmail(email);
        return user;
    }

    private String encodePassword(String pwd) {
        return passwordEncoder.encode(pwd);
    }

    private void startActivation(User user) throws MessagingException, UnsupportedEncodingException, NoSuchAlgorithmException {
        this.activationProces.startActivationProcess(user);
    }

    private void addUser(User user, UserRole userRole) {
        this.userRepository.addNewUser(user);
        this.userRepository.addNewUserRole(userRole);
    }

    private void addUserSettings(User user) {
        this.userRepository.addNewUserSettings(new UserSettings(user));
    }

    public boolean isUserExist(String login) {
        return this.userRepository.findByUserName(login) == null;
    }
}
