package models;

import interfaces.IFlyable;
import interfaces.ITransport;

public class AirTransport extends Transport implements IFlyable, ITransport {
    private double altitude;
    private String airline;
    private String flightStatus;
    
    public AirTransport(String id, String name, int capacity, double speed, 
                       String airline) {
        super(id, name, capacity, speed);
        this.airline = airline;
        this.altitude = 0;
        this.flightStatus = "GROUNDED";
    }
    
    @Override
    public String getType() {
        return "AIR";
    }
    
    @Override
    public void displayInfo() {
        System.out.printf("%s - Airline: %s | Altitude: %.1f ft | Status: %s | %s%n",
                         name, airline, altitude, flightStatus,
                         isAvailable ? "Available" : "Busy");
    }
    @Override
    public void takeOff() {
        System.out.printf("%s is taking off!%n", name);
        this.altitude = 35000;
        this.flightStatus = "IN_FLIGHT";
    }
    
    @Override
    public void land() {
        System.out.printf(" %s is landing!%n", name);
        this.altitude = 0;
        this.flightStatus = "LANDED";
    }
    
    @Override
    public String getFlightStatus() {
        return flightStatus;
    }
    
    @Override
    public void fly() {
        System.out.printf(" %s is flying at %.1f ft altitude%n", name, altitude);
        this.flightStatus = "CRUISING";
    }
    

    
    public String getAirline() { 
        return airline; 
    }
    
    public double getAltitude() { 
        return altitude; 
    }
    
    public void emergencyLand() {
        System.out.printf("%s making emergency landing!%n", name);
        this.altitude = 0;
        this.flightStatus = "EMERGENCY_LANDED";
    }
    
    public void increaseAltitude(double feet) {
        this.altitude += feet;
        System.out.printf("%s climbed to %.1f ft%n", name, altitude);
    }
}