package interfaces;

import models.User;
import java.util.Map;

public interface IAuthService {
    // Basic Authentication Methods
    boolean register(String username, String password, String email, String role);
    boolean login(String username, String password);
    void logout();
    User getCurrentUser();
    boolean isLoggedIn();
    Map<String, User> getUsers();
    
    // User Management
    boolean changePassword(String username, String oldPassword, String newPassword);
    boolean deactivateUser(String username);
    boolean activateUser(String username);
    
    // Account Status
    boolean isAccountLocked(String username);
    boolean isAccountActive(String username);
    
    // Session Management
    String getSessionInfo();
    void clearSession();
}