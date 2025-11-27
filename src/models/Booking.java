package models;
import java.time.LocalDateTime;
import java.util.UUID;

public class Booking {
    private String bookingId;
    private String username;
    private String transportId;
    private LocalDateTime bookingTime;

    private String Status;
    public Booking(String username, String transportId){
        this.bookingId = UUID.randomUUID().toString().substring(0,8).toUpperCase();
        this.username = username;
        this.transportId = transportId;
        this.bookingTime = LocalDateTime.now();
        this.Status = "CONFIRMED";
        

    }

    public String getBookingId(){
        return bookingId;
    }

    public String getUsername (){
        return username;
    }

    public String getTransportId(){
        return transportId;
    }

    public LocalDateTime getBookingTime(){
        return bookingTime;
    }

    public String getStatus(){
        return Status;
    }

    public void cancel(){
        this.Status = "CANCELLED";
    }

    public void complete (){
        this.Status = "Completed";
    }
    @Override
    public String toString(){
        return String.format("Booking[ID: %s,User: %s, Transport : %s , Time : %s, Status : %s ]",bookingId,username,transportId,bookingTime,Status);
    }
    
}
