package models;

import interfaces.ISailable;
import interfaces.ITransport;

public class SeaTransport extends Transport implements ISailable, ITransport {
    private double depth;
    private String port;
    private String sailingStatus;
    
    public SeaTransport(String id, String name, int capacity, double speed, 
                       String port) {
        super(id, name, capacity, speed);
        this.port = port;
        this.depth = 0;
        this.sailingStatus = "DOCKED";
    }
    
    @Override
    public String getType() {
        return "SEA";
    }
    
    @Override
    public void displayInfo() {
        System.out.printf("⛴️  %s - Port: %s | Depth: %.1f m | Status: %s | %s%n",
                         name, port, depth, sailingStatus,
                         isAvailable ? "✅ Available" : "❌ Busy");
    }
    
    // ========== ISailable Interface Methods ==========
    
    @Override
    public void dock() {
        System.out.printf("⚓ %s is docking!%n", name);
        this.depth = 0;
        this.sailingStatus = "DOCKED";
    }
    
    @Override
    public void cruise() {
        System.out.printf("🚢 %s is cruising!%n", name);
        this.depth = 100;
        this.sailingStatus = "CRUISING";
    }
    
    @Override
    public String getSailingStatus() {
        return sailingStatus;
    }
    
    @Override
    public void setSail() {
        System.out.printf("⛵ %s is setting sail from %s!%n", name, port);
        this.depth = 50;
        this.sailingStatus = "SAILING";
    }
    
    // ========== Additional Methods ==========
    
    public String getPort() { 
        return port; 
    }
    
    public double getDepth() { 
        return depth; 
    }
    
    public void anchor() {
        System.out.printf("⚓ %s dropping anchor!%n", name);
        this.sailingStatus = "ANCHORED";
    }
}