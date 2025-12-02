package services;

import interfaces.IUserLockService;
import models.UserLock;
import java.util.*;

public class UserLockService implements IUserLockService {
    private Map<String, UserLock> lockedUsers;
    private List<String> lockHistory;

    public UserLockService() {
        this.lockedUsers = new HashMap<>();
        this.lockHistory = new ArrayList<>();
    }

    @Override
    public boolean lockUser(String username, String lockedBy) {
        if (!isUserLocked(username)) {
            UserLock lock = new UserLock(username, lockedBy);
            lockedUsers.put(username, lock);
            
            lockHistory.add("User locked: " + username + " by " + lockedBy + " at " + new Date());
            System.out.println("User locked: " + username);
            System.out.println("Unlock Key: " + lock.getUnlockKey());
            return true;
        }
        System.out.println("User already locked: " + username);
        return false;
    }

    @Override
    public boolean unlockUser(String username, String unlockKey) {
        UserLock lock = lockedUsers.get(username);
        if (lock != null && lock.isValidKey(unlockKey)) {
            lock.unlock();
            lockedUsers.remove(username);
            
            lockHistory.add("User unlocked (key): " + username + " at " + new Date());
            System.out.println("User unlocked: " + username);
            return true;
        }
        System.out.println("Invalid unlock key or user not found!");
        return false;
    }

    @Override
    public boolean unlockWithAdminPower(String username) {
        UserLock lock = lockedUsers.get(username);
        if (lock != null) {
            lock.unlock();
            lockedUsers.remove(username);
            
            lockHistory.add("User unlocked (admin): " + username + " at " + new Date());
            System.out.println("User unlocked by admin: " + username);
            return true;
        }
        System.out.println("User not locked: " + username);
        return false;
    }

    @Override
    public boolean forceUnlockAll() {
        if (lockedUsers.isEmpty()) {
            System.out.println("ℹ️ No locked users to unlock!");
            return false;
        }
        
        int count = lockedUsers.size();
        lockedUsers.clear();
        lockHistory.add("All users force unlocked at " + new Date());
        
        System.out.println("Force unlocked all " + count + " users!");
        return true;
    }

    @Override
    public boolean isUserLocked(String username) {
        return lockedUsers.containsKey(username);
    }

    @Override
    public UserLock getLockInfo(String username) {
        UserLock lock = lockedUsers.get(username);
        if (lock == null) {
            System.out.println("No lock info for: " + username);
        }
        return lock;
    }

    @Override
    public String getUnlockKey(String username) {
        UserLock lock = lockedUsers.get(username);
        return lock != null ? lock.getUnlockKey() : "NOT_LOCKED";
    }

    @Override
    public String getLockedBy(String username) {
        UserLock lock = lockedUsers.get(username);
        return lock != null ? lock.getLockedBy() : "NOT_LOCKED";
    }

    @Override
    public List<UserLock> getAllLockedUsers() {
        return new ArrayList<>(lockedUsers.values());
    }

    @Override
    public List<String> getLockedUsernames() {
        return new ArrayList<>(lockedUsers.keySet());
    }

    @Override
    public int getLockedUsersCount() {
        return lockedUsers.size();
    }

    @Override
    public boolean removeLock(String username) {
        if (lockedUsers.remove(username) != null) {
            lockHistory.add("Lock removed: " + username + " at " + new Date());
            System.out.println("Lock removed: " + username);
            return true;
        }
        return false;
    }

    @Override
    public List<String> getLockHistory() {
        return new ArrayList<>(lockHistory);
    }

    @Override
    public int autoUnlockExpiredLocks(int hoursThreshold) {
        int unlockedCount = 0;
        List<String> toUnlock = new ArrayList<>();
        
        for (UserLock lock : lockedUsers.values()) {
            java.time.LocalDateTime now = java.time.LocalDateTime.now();
            long hoursLocked = java.time.Duration.between(lock.getLockedAt(), now).toHours();
            
            if (hoursLocked >= hoursThreshold) {
                toUnlock.add(lock.getUsername());
            }
        }
        
        for (String username : toUnlock) {
            unlockWithAdminPower(username);
            unlockedCount++;
        }
        
        if (unlockedCount > 0) {
            System.out.println("Auto-unlocked " + unlockedCount + " expired locks!");
        }
        
        return unlockedCount;
    }

    @Override
    public void clearExpiredLocks() {
        autoUnlockExpiredLocks(24); // Clear locks older than 24 hours
    }

    @Override
    public void clearLockHistory() {
        lockHistory.clear();
        System.out.println("Lock history cleared!");
    }

    @Override
    public String getLockDuration(String username) {
        UserLock lock = lockedUsers.get(username);
        if (lock != null) {
            return lock.getLockDuration();
        }
        return "NOT_LOCKED";
    }

    @Override
    public boolean isLockExpired(String username, int hoursThreshold) {
        UserLock lock = lockedUsers.get(username);
        if (lock != null) {
            java.time.LocalDateTime now = java.time.LocalDateTime.now();
            long hoursLocked = java.time.Duration.between(lock.getLockedAt(), now).toHours();
            return hoursLocked >= hoursThreshold;
        }
        return false;
    }

    @Override
    public boolean isValidUnlockKey(String username, String key) {
        UserLock lock = lockedUsers.get(username);
        return lock != null && lock.isValidKey(key);
    }

    @Override
    public void displayLockedUsers() {
        System.out.println("\nLOCKED USERS (" + lockedUsers.size() + " users):");
        System.out.println("=".repeat(50));
        
        if (lockedUsers.isEmpty()) {
            System.out.println("No locked users.");
        } else {
            for (UserLock lock : lockedUsers.values()) {
                System.out.println("• " + lock.getUsername() + 
                    " - Locked by: " + lock.getLockedBy() + 
                    " - Key: " + lock.getUnlockKey());
            }
        }
    }

    @Override
    public void displayLockDetails(String username) {
        UserLock lock = lockedUsers.get(username);
        if (lock != null) {
            System.out.println("\nLOCK DETAILS for " + username + ":");
            System.out.println("=".repeat(40));
            System.out.println("Locked By: " + lock.getLockedBy());
            System.out.println("Locked At: " + lock.getLockedAt());
            System.out.println("Unlock Key: " + lock.getUnlockKey());
            System.out.println("Duration: " + lock.getLockDuration());
        } else {
            System.out.println("User not locked: " + username);
        }
    }

    @Override
    public String generateLockReport() {
        StringBuilder report = new StringBuilder();
        report.append("LOCK REPORT\n");
        report.append("=".repeat(40)).append("\n");
        report.append("Total Locked Users: ").append(getLockedUsersCount()).append("\n");
        report.append("Lock History Entries: ").append(lockHistory.size()).append("\n");
        
        for (UserLock lock : lockedUsers.values()) {
            report.append("\n• ").append(lock.getUsername())
                  .append(" - Locked by: ").append(lock.getLockedBy())
                  .append(" - Duration: ").append(lock.getLockDuration());
        }
        
        return report.toString();
    }
}