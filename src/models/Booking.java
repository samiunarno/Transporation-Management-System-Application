package models;

import interfaces.IBookable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Booking implements IBookable {
    private String bookingId;
    private String username;
    private String transportId;
    private LocalDateTime bookingTime;
    private String status;
    
    public Booking(String username, String transportId) {
        this.bookingId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.username = username;
        this.transportId = transportId;
        this.bookingTime = LocalDateTime.now();
        this.status = "CONFIRMED";
    }
    
    // ========== IBookable Interface Methods ==========
    
    @Override
    public String getStatus() {
        return status;
    }
    
    @Override
    public void cancel() {
        this.status = "CANCELLED";
    }
    
    @Override
    public void complete() {
        this.status = "COMPLETED";
    }
    
    @Override
    public boolean isConfirmed() {
        return status.equals("CONFIRMED");
    }
    
    // ========== Additional Methods ==========
    
    public String getBookingId() {
        return bookingId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getTransportId() {
        return transportId;
    }
    
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }
    
    public boolean isCancelled() {
        return status.equals("CANCELLED");
    }
    
    public boolean isCompleted() {
        return status.equals("COMPLETED");
    }
    
    public void reschedule() {
        this.bookingTime = LocalDateTime.now();
        this.status = "RESCHEDULED";
        System.out.println("Booking " + bookingId + " has been rescheduled");
    }
    
    @Override
    public String toString() {
        return String.format("Booking[ID: %s, User: %s, Transport: %s, Time: %s, Status: %s]",
                           bookingId, username, transportId, bookingTime, status);
    }
}