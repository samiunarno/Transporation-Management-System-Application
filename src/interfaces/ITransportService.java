package interfaces;

import models.Transport;
import java.util.List;
import java.util.Map;

public interface ITransportService {
    void addTransport(Transport transport);
    boolean removeTransport(String id);
    Transport findTransport(String id);
    boolean updateTransport(String id, Transport newTransport);
    

    List<Transport> getAllTransports();
    List<Transport> getAvailableTransports();
    List<Transport> getTransportsByType(String type);
    List<Transport> searchTransports(String keyword);
    

    boolean isTransportAvailable(String id);
    boolean setTransportAvailability(String id, boolean available);

    int getTotalTransports();
    int getAvailableCount();
    Map<String, Integer> getTransportStatistics();
    

    String generateId(String type);
    String getNextAvailableId();
    

    void displayAllTransports();
    void displayAvailableTransports();
    void displayTransportDetails(String id);
}