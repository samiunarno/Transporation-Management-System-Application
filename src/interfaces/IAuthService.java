package interfaces;

import models.User;
import java.util.Map;

public interface IAuthService {

    boolean register(String username, String password, String email, String role);
    boolean login(String username, String password);
    void logout();
    User getCurrentUser();
    boolean isLoggedIn();
    Map<String, User> getUsers();
    boolean changePassword(String username, String oldPassword, String newPassword);
    boolean deactivateUser(String username);
    boolean activateUser(String username);
    

    boolean isAccountLocked(String username);
    boolean isAccountActive(String username);
    String getSessionInfo();
    void clearSession();
}