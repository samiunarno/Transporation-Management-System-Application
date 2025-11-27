package models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserLock implements Serializable {
    private String username;
    private boolean isLocked;
    private LocalDateTime lockedAt;
    private String lockedBy;

    private String Unlockedkey;

    public UserLock(String username , String lockedBy){
        this.username = username;
        this.isLocked = true;
        this.lockedAt = LocalDateTime.now();
        this.lockedBy = lockedBy;
        this.Unlockedkey = generateUnlockKey();
    }
    private String generateUnlockKey(){
        return "UNLOCK*" + username.toUpperCase() + "_"+System.currentTimeMillis()% 10000;
    }

    public String getUsername(){
        return username;
    }
    public boolean isLocked(){
        return isLocked;
    }
    public LocalDateTime getLockedAt(){
        return lockedAt;
    }
    public String getLockedBy(){
        return lockedBy;
    }
    public String getUnlockKey(){
        return Unlockedkey;
    }

    public void Unlock(){
        this.isLocked = false;
    }
    
    @Override
    public  String toString (){
        return String.format("UserLock{user='%s' , locked = '%s' , key = '%s' by = '%s'",username,isLocked,Unlockedkey,lockedBy);

        
    }

    
}
