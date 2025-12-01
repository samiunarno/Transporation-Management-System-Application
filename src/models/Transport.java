package models;

import interfaces.ITransport;

public abstract class Transport implements ITransport {
    protected String id;
    protected String name;
    protected int capacity;
    protected double speed;
    protected boolean isAvailable;
    
    public Transport(String id, String name, int capacity, double speed) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.speed = speed;
        this.isAvailable = true;
    }
    
    @Override
    public String getId() { 
        return id; 
    }
    
    @Override
    public String getName() { 
        return name; 
    }
    
    @Override
    public int getCapacity() { 
        return capacity; 
    }
    
    @Override
    public double getSpeed() { 
        return speed; 
    }
    
    @Override
    public boolean isAvailable() { 
        return isAvailable; 
    }
    
    @Override
    public void setAvailable(boolean available) { 
        isAvailable = available; 
    }
    
    @Override
    public abstract String getType();
    
    @Override
    public abstract void displayInfo();
    
    // Additional utility method
    public double calculateTravelTime(double distance) {
        if (speed > 0) {
            return distance / speed;
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return String.format("%s [ID: %s, Capacity: %d, Speed: %.1f km/h, Available: %s]",
                           name, id, capacity, speed, isAvailable ? "✅" : "❌");
    }
}