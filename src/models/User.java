package models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable{
    private String username;
    private String password;
    private String email;
    private String role;

    private LocalDateTime createdAt;
    private boolean isActive;
    private int failedLoginAttempts;

    public User(String username , String password , String email , String role ){
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.isActive = true;
        this.failedLoginAttempts = 0;
    }

    public String getUsername(){
        return username;
    }
    public String getpassword(){
        return password;
    }
    public String getEmail(){
        return email;
    }
    public String getRole(){
        return role;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    public boolean isActive(){
        return isActive;
    }
    public int getFailedLoginAttempts(){
        return failedLoginAttempts;
    }
    public void setPassword(String password){
        this.password= password;
    }

    public void serActive(boolean active){
        isActive = active;
    }
    public void increamentFailedAttempts(){
        this.failedLoginAttempts++;
    }
    public void resetFailedAttempts(){
        this.failedLoginAttempts = 0;
    }
    public boolean isLocked(){
        return failedLoginAttempts >= 3;
    }

    @Override
    public String toString(){
        return String.format("User {username='%s', role = '%s', active= '%s' , failedAttempts = %d}", username,role,isActive,failedLoginAttempts);

    }
}
