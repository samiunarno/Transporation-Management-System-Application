package models;

import interfaces.IUser;
import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements IUser, Serializable {
    private String username;
    private String password;
    private String email;
    private String role;
    private LocalDateTime createdAt;
    private boolean isActive;
    private int failedLoginAttempts;

    public User(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.isActive = true;
        this.failedLoginAttempts = 0;
    }
    

    
    @Override
    public String getUsername() {
        return username;
    }
    
    @Override
    public String getEmail() {
        return email;
    }
    
    @Override
    public String getRole() {
        return role;
    }
    
    @Override
    public boolean isActive() {
        return isActive;
    }
    
    @Override
    public boolean isLocked() {
        return failedLoginAttempts >= 3;
    }
    
    @Override
    public void incrementFailedAttempts() {
        this.failedLoginAttempts++;
    }
    
    @Override
    public void resetFailedAttempts() {
        this.failedLoginAttempts = 0;
    }
    
    // ========== Additional Methods ==========
    
    public String getPassword() {
        return password;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public boolean isAdmin() {
        return role.equals("ADMIN");
    }
    
    public boolean isOperator() {
        return role.equals("OPERATOR");
    }
    
    public boolean isPassenger() {
        return role.equals("PASSENGER");
    }
    
    public void updateRole(String newRole) {
        this.role = newRole;
        System.out.println("👤 User " + username + " role updated to " + newRole);
    }
    
    @Override
    public String toString() {
        return String.format("User{username='%s', role='%s', active=%s, failedAttempts=%d}", 
                           username, role, isActive, failedLoginAttempts);
    }
}