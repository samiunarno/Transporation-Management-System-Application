package interfaces;

import models.Transport;
import java.util.List;
import java.util.Map;

public interface ITransportService {
    // Transport Management
    void addTransport(Transport transport);
    boolean removeTransport(String id);
    Transport findTransport(String id);
    boolean updateTransport(String id, Transport newTransport);
    
    // Transport Retrieval
    List<Transport> getAllTransports();
    List<Transport> getAvailableTransports();
    List<Transport> getTransportsByType(String type);
    List<Transport> searchTransports(String keyword);
    
    // Transport Status
    boolean isTransportAvailable(String id);
    boolean setTransportAvailability(String id, boolean available);
    
    // Transport Information
    int getTotalTransports();
    int getAvailableCount();
    Map<String, Integer> getTransportStatistics();
    
    // ID Generation
    String generateId(String type);
    String getNextAvailableId();
    
    // Display Methods
    void displayAllTransports();
    void displayAvailableTransports();
    void displayTransportDetails(String id);
}