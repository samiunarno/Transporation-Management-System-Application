package services;

import models.User;
import java.util.*;

public class AuthService {
    private Map<String, User> users;
    
    private User currentUser;
    private UserLockService lockService;

    public AuthService() {
        this.users = new HashMap<>();
        this.lockService = new UserLockService();
        initializeDefaultUsers();
    }

    private void initializeDefaultUsers() {
        users.put("admin",
            new User("admin", "admin123", "admin@transport.com", "ADMIN"));

        users.put("operator",
            new User("operator", "op123", "operator@transport.com", "OPERATOR"));

        users.put("john",
            new User("john", "pass123", "john@email.com", "PASSENGER"));
    }

    public boolean register(String username, String password, String email, String role) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists!");
            return false;
        }

        users.put(username, new User(username, password, email, role));
        System.out.println("Registration successful!");
        return true;
    }

    public boolean login(String username, String password) {

        if (lockService.isUserLocked(username)) {
            System.out.println("Account is locked. Contact administrator.");
            return false;
        }

        User user = users.get(username);

        if (user == null) {
            System.out.println("User not found!");
            return false;
        }

        if (!user.isActive()) {
            System.out.println("Account is inactive!");
            return false;
        }

        if (password.equals(user.getPassword())) {
            currentUser = user;
            user.resetFailedAttempts();
            System.out.printf("Welcome back, %s!%n", user.getUsername());
            return true;
        }

        user.incrementFailedAttempts();
        int attemptsLeft = 3 - user.getFailedLoginAttempts();
        System.out.printf("nvalid password! Attempts left: %d%n", attemptsLeft);

        if (user.getFailedLoginAttempts() >= 3) {
            lockService.lockUser(username, "SYSTEM");
            System.out.println("Account locked due to too many failed attempts!");
        }

        return false;
    }

    public void logout() {
        if (currentUser != null) {
            System.out.printf("Goodbye, %s!%n", currentUser.getUsername());
            currentUser = null;
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public UserLockService getLockService() {
        return lockService;
    }

    public Map<String, User> getUsers() {
        return new HashMap<>(users);
    }
}
