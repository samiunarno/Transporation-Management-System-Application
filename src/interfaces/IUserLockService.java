package interfaces;

import models.UserLock;
import java.util.List;

public interface IUserLockService {
    // Lock Operations
    boolean lockUser(String username, String lockedBy);
    boolean unlockUser(String username, String unlockKey);
    boolean unlockWithAdminPower(String username);
    boolean forceUnlockAll();
    
    // Lock Information
    boolean isUserLocked(String username);
    UserLock getLockInfo(String username);
    String getUnlockKey(String username);
    String getLockedBy(String username);
    
    // Lock Management
    List<UserLock> getAllLockedUsers();
    List<String> getLockedUsernames();
    int getLockedUsersCount();
    boolean removeLock(String username);
    
    // Lock History & Auto-cleanup
    List<String> getLockHistory();
    int autoUnlockExpiredLocks(int hoursThreshold);
    void clearExpiredLocks();
    void clearLockHistory();
    
    // Lock Status
    String getLockDuration(String username);
    boolean isLockExpired(String username, int hoursThreshold);
    boolean isValidUnlockKey(String username, String key);
    
    // Display Methods
    void displayLockedUsers();
    void displayLockDetails(String username);
    String generateLockReport();
}