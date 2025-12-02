package models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserLock implements Serializable {
    private String username;
    private boolean isLocked;
    private LocalDateTime lockedAt;
    private String lockedBy;
    private String unlockKey;
    
    public UserLock(String username, String lockedBy) {
        this.username = username;
        this.isLocked = true;
        this.lockedAt = LocalDateTime.now();
        this.lockedBy = lockedBy;
        this.unlockKey = generateUnlockKey();
    }
    
    private String generateUnlockKey() {
        return "UNLOCK_" + username.toUpperCase() + "_" + 
               System.currentTimeMillis() % 10000;
    }
    
    public String getUsername() { 
        return username; 
    }
    
    public boolean isLocked() { 
        return isLocked; 
    }
    
    public LocalDateTime getLockedAt() { 
        return lockedAt; 
    }
    
    public String getLockedBy() { 
        return lockedBy; 
    }
    
    public String getUnlockKey() { 
        return unlockKey; 
    }
    
    public void unlock() {
        this.isLocked = false;
        System.out.println("Account " + username + " has been unlocked");
    }
    
    public boolean isValidKey(String key) {
        return unlockKey.equals(key);
    }
    
    public String getLockDuration() {
        LocalDateTime now = LocalDateTime.now();
        long hours = java.time.Duration.between(lockedAt, now).toHours();
        return hours + " hours";
    }
    
    @Override
    public String toString() {
        return String.format("UserLock{user='%s', locked=%s, key='%s', by='%s', duration='%s'}", 
                           username, isLocked, unlockKey, lockedBy, getLockDuration());
    }
}