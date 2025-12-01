package services;

import models.UserLock;
import java.util.*;

public class UserLockService {
    private Map<String, UserLock> lockedUsers;

    public UserLockService() {
        this.lockedUsers = new HashMap<>();
    }

    public boolean lockUser(String username, String adminUsername) {
        if (!isUserLocked(username)) {
            UserLock lock = new UserLock(username, adminUsername);
            lockedUsers.put(username, lock);
            return true;
        }
        return false;
    }

    public UserLock getLockInfo(String username) {
        return lockedUsers.get(username);
    }

    public List<UserLock> getAllLockedUsers() {
        return new ArrayList<>(lockedUsers.values());
    }

    public boolean unlockWithAdminPower(String username) {
        UserLock lock = lockedUsers.get(username);
        if (lock != null) {
            lock.unlock();
            lockedUsers.remove(username);
            return true;
        }
        return false;
    }

    public boolean isUserLocked(String username) {
        return lockedUsers.containsKey(username);
    }
}
