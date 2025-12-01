package services;

import interfaces.ITransportService;
import models.Transport;
import models.AirTransport;
import models.SeaTransport;
import java.util.*;

public class TransportService implements ITransportService {
    private Map<String, Transport> transports;
    private int transportCounter;
    private List<String> operationLog;

    public TransportService() {
        this.transports = new HashMap<>();
        this.transportCounter = 1;
        this.operationLog = new ArrayList<>();
        initializeSampleData();
    }

    private void initializeSampleData() {
        addTransport(new AirTransport("AIR001", "Boeing 777", 320, 900, "China Star Airways"));
        addTransport(new SeaTransport("SEA001", "Hai Long", 800, 40, "Shanghai Port"));
        System.out.println("✅ Sample transports initialized");
    }

    @Override
    public void addTransport(Transport transport) {
        transports.put(transport.getId(), transport);
        operationLog.add("Added transport: " + transport.getId());
        System.out.println("✅ Transport added: " + transport.getId());
    }

    @Override
    public boolean removeTransport(String id) {
        Transport removed = transports.remove(id);
        if (removed != null) {
            operationLog.add("Removed transport: " + id);
            System.out.println("✅ Transport removed: " + id);
            return true;
        }
        System.out.println("❌ Transport not found: " + id);
        return false;
    }

    @Override
    public Transport findTransport(String id) {
        Transport transport = transports.get(id);
        if (transport == null) {
            System.out.println("⚠️ Transport not found: " + id);
        }
        return transport;
    }

    @Override
    public boolean updateTransport(String id, Transport newTransport) {
        if (transports.containsKey(id)) {
            transports.put(id, newTransport);
            operationLog.add("Updated transport: " + id);
            System.out.println("✅ Transport updated: " + id);
            return true;
        }
        System.out.println("❌ Transport not found for update: " + id);
        return false;
    }

    @Override
    public List<Transport> getAllTransports() {
        return new ArrayList<>(transports.values());
    }

    @Override
    public List<Transport> getAvailableTransports() {
        List<Transport> available = new ArrayList<>();
        for (Transport t : transports.values()) {
            if (t.isAvailable()) {
                available.add(t);
            }
        }
        return available;
    }

    @Override
    public List<Transport> getTransportsByType(String type) {
        List<Transport> filtered = new ArrayList<>();
        for (Transport t : transports.values()) {
            if (t.getType().equalsIgnoreCase(type)) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    @Override
    public List<Transport> searchTransports(String keyword) {
        List<Transport> results = new ArrayList<>();
        for (Transport t : transports.values()) {
            if (t.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                t.getId().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(t);
            }
        }
        return results;
    }

    @Override
    public boolean isTransportAvailable(String id) {
        Transport transport = transports.get(id);
        return transport != null && transport.isAvailable();
    }

    @Override
    public boolean setTransportAvailability(String id, boolean available) {
        Transport transport = transports.get(id);
        if (transport != null) {
            transport.setAvailable(available);
            operationLog.add("Set availability: " + id + " = " + available);
            System.out.println("✅ Transport " + id + " availability set to: " + available);
            return true;
        }
        return false;
    }

    @Override
    public int getTotalTransports() {
        return transports.size();
    }

    @Override
    public int getAvailableCount() {
        return getAvailableTransports().size();
    }

    @Override
    public Map<String, Integer> getTransportStatistics() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("Total", getTotalTransports());
        stats.put("Available", getAvailableCount());
        stats.put("Air", getTransportsByType("AIR").size());
        stats.put("Sea", getTransportsByType("SEA").size());
        return stats;
    }

    @Override
    public String generateId(String type) {
        String prefix = type.equalsIgnoreCase("AIR") ? "AIR" : "SEA";
        String id = String.format("%s%03d", prefix, transportCounter++);
        System.out.println("🆔 Generated ID: " + id);
        return id;
    }

    @Override
    public String getNextAvailableId() {
        return generateId("AIR");
    }

    @Override
    public void displayAllTransports() {
        System.out.println("\n🚀 ALL TRANSPORTS (" + transports.size() + " total):");
        System.out.println("=".repeat(50));
        if (transports.isEmpty()) {
            System.out.println("No transports available.");
        } else {
            for (Transport t : transports.values()) {
                t.displayInfo();
            }
        }
    }

    @Override
    public void displayAvailableTransports() {
        List<Transport> available = getAvailableTransports();
        System.out.println("\n✅ AVAILABLE TRANSPORTS (" + available.size() + " available):");
        System.out.println("=".repeat(50));
        if (available.isEmpty()) {
            System.out.println("No available transports.");
        } else {
            for (Transport t : available) {
                t.displayInfo();
            }
        }
    }

    @Override
    public void displayTransportDetails(String id) {
        Transport transport = transports.get(id);
        if (transport != null) {
            System.out.println("\n📋 TRANSPORT DETAILS:");
            System.out.println("=".repeat(30));
            transport.displayInfo();
        } else {
            System.out.println("❌ Transport not found: " + id);
        }
    }

    // Additional helper method
    public void showStatistics() {
        Map<String, Integer> stats = getTransportStatistics();
        System.out.println("\n📊 TRANSPORT STATISTICS:");
        System.out.println("=".repeat(30));
        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}