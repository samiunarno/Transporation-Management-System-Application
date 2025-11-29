package services;

import models.Transport;
import models.AirTransport;
import models.SeaTransport;
import java.util.*;

public class TransportService {
    private Map<String, Transport> transports;
    private int transportCounter;

    public TransportService() {
        this.transports = new HashMap<>();
        this.transportCounter = 1;
        initializeSampleData();
    }

    private void initializeSampleData() {
        addTransport(new AirTransport("AIR003", "Boeing 777", 320, 900, "China Star Airways"));
        addTransport(new SeaTransport("SEA003", "Hai Long", 800, 40, "Shanghai Port"));

    }

    public void addTransport(Transport t) {
        transports.put(t.getId(), t);
    }

    public boolean removeTransport(String id) {
        return transports.remove(id) != null;
    }

    public Transport findTransport(String id) {
        return transports.get(id);
    }

    public List<Transport> getAvailableTransports() {
        List<Transport> available = new ArrayList<>();
        for (Transport t : transports.values()) {
            if (t.isAvailable()) {
                available.add(t);
            }
        }
        return available;
    }

    public List<Transport> getTransportsByType(String type) {
        List<Transport> filtered = new ArrayList<>();
        for (Transport t : transports.values()) {
            if (t.getType().equals(type)) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    public String generateId(String type) {
        String prefix = type.equals("AIR") ? "AIR" : "SEA";
        return String.format("%s%03d", prefix, transportCounter++);
    }

    public int getTotalTransports() {
        return transports.size();
    }

    public int getAvailableCount() {
        return getAvailableTransports().size();
    }
}
