package interfaces;

public interface ITransport {
    String getId();
    String getName();
    int getCapacity();
    double getSpeed();
    boolean isAvailable();
    void setAvailable(boolean available);
    void displayInfo();
    String getType();
}