package services;
import interfaces.IAuthService;
import models.User;
import java.util.*;
import java.io.*;

public class AuthService implements IAuthService {
    private Map<String, User> users;
    private User currentUser;
    private UserLockService lockService;
    private static final String USER_FILE = "data/users.txt";

    public AuthService() {
        this.users = new HashMap<>();
        this.lockService = new UserLockService();
        loadUsersFromFile();
    }

    private void loadUsersFromFile() {
        File file = new File(USER_FILE);
        if (!file.exists()) {
            initializeDefaultUsers();
            saveUsersToFile();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    User user = new User(parts[0], parts[1], parts[2], parts[3]);
                    users.put(user.getUsername(), user);
                }
            }
            System.out.println("Users loaded from file: " + users.size() + " users");
        } catch (IOException e) {
            System.out.println("Error loading users from file: " + e.getMessage());
            initializeDefaultUsers();
        }
    }

    private void saveUsersToFile() {
        try {
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
                for (User user : users.values()) {
                    String line = user.getUsername() + "," + 
                                 user.getPassword() + "," + 
                                 user.getEmail() + "," + 
                                 user.getRole();
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving users to file: " + e.getMessage());
        }
    }

    private void initializeDefaultUsers() {
        users.put("admin", new User("admin", "admin123", "admin@transport.com", "ADMIN"));
        users.put("operator", new User("operator", "op123", "operator@transport.com", "OPERATOR"));
        users.put("john", new User("john", "pass123", "john@email.com", "PASSENGER"));
    }

    @Override
    public boolean register(String username, String password, String email, String role) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists!");
            return false;
        }

        User newUser = new User(username, password, email, role);
        users.put(username, newUser);
        saveUsersToFile();
        System.out.println("Registration successful!");
        logActivity("REGISTER", username);
        return true;
    }

    @Override
    public boolean login(String username, String password) {
        if (lockService.isUserLocked(username)) {
            System.out.println("Account is locked. Contact administrator.");
            logActivity("LOGIN_FAILED_LOCKED", username);
            return false;
        }

        User user = users.get(username);
        if (user == null) {
            System.out.println("User not found!");
            logActivity("LOGIN_FAILED_NOT_FOUND", username);
            return false;
        }

        if (!user.isActive()) {
            System.out.println("Account is inactive!");
            logActivity("LOGIN_FAILED_INACTIVE", username);
            return false;
        }

        if (password.equals(user.getPassword())) {
            currentUser = user;
            user.resetFailedAttempts();
            System.out.println("Welcome back, " + user.getUsername() + "!");
            logActivity("LOGIN_SUCCESS", username);
            return true;
        }

        user.incrementFailedAttempts();
        int attemptsLeft = 3 - user.getFailedLoginAttempts();
        System.out.println("Invalid password! Attempts left: " + attemptsLeft);
        logActivity("LOGIN_FAILED_PASSWORD", username);

        if (user.getFailedLoginAttempts() >= 3) {
            lockService.lockUser(username, "SYSTEM");
            System.out.println("Account locked due to too many failed attempts!");
            logActivity("ACCOUNT_LOCKED", username);
        }

        return false;
    }

    @Override
    public void logout() {
        if (currentUser != null) {
            System.out.println("Goodbye, " + currentUser.getUsername() + "!");
            logActivity("LOGOUT", currentUser.getUsername());
            currentUser = null;
        }
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    @Override
    public Map<String, User> getUsers() {
        return new HashMap<>(users);
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            saveUsersToFile();
            System.out.println("Password changed successfully!");
            logActivity("PASSWORD_CHANGED", username);
            return true;
        }
        System.out.println("Password change failed!");
        return false;
    }

    @Override
    public boolean deactivateUser(String username) {
        User user = users.get(username);
        if (user != null) {
            user.setActive(false);
            saveUsersToFile();
            System.out.println("User deactivated: " + username);
            logActivity("USER_DEACTIVATED", username);
            return true;
        }
        return false;
    }

    @Override
    public boolean activateUser(String username) {
        User user = users.get(username);
        if (user != null) {
            user.setActive(true);
            saveUsersToFile();
            System.out.println("User activated: " + username);
            logActivity("USER_ACTIVATED", username);
            return true;
        }
        return false;
    }

    @Override
    public boolean isAccountLocked(String username) {
        return lockService.isUserLocked(username);
    }

    @Override
    public boolean isAccountActive(String username) {
        User user = users.get(username);
        return user != null && user.isActive();
    }

    @Override
    public String getSessionInfo() {
        if (currentUser == null) {
            return "No active session";
        }
        return "User: " + currentUser.getUsername() + ", Role: " + currentUser.getRole();
    }

    @Override
    public void clearSession() {
        currentUser = null;
        System.out.println("Session cleared!");
    }

    private void logActivity(String activity, String username) {
        try {
            File logDir = new File("logs");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("logs/activity.log", true))) {
                String timestamp = new java.util.Date().toString();
                String logEntry = timestamp + " - " + activity + " - User: " + username;
                writer.write(logEntry);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }
}