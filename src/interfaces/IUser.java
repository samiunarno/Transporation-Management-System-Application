package interfaces;

public interface IUser {
    String getUsername();
    String getEmail();
    String getRole();
    boolean isActive();
    boolean isLocked();
    void incrementFailedAttempts();
    void resetFailedAttempts();
}