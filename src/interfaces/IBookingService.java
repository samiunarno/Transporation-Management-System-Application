package interfaces;

import models.Booking;
import java.util.List;
import java.util.Map;

public interface IBookingService {

    Booking createBooking(String username, String transportId);
    boolean cancelBooking(String bookingId);
    boolean confirmBooking(String bookingId);
    boolean completeBooking(String bookingId);
    boolean rescheduleBooking(String bookingId);
    

    Booking findBookingById(String bookingId);
    List<Booking> getUserBookings(String username);
    List<Booking> getAllBookings();
    List<Booking> getTodayBookings();
    List<Booking> getPendingBookings();
    String getBookingStatus(String bookingId);
    boolean isBookingConfirmed(String bookingId);
    boolean isBookingCancelled(String bookingId);
    boolean isBookingCompleted(String bookingId);
    
    // Booking Information
    int getTotalBookings();
    int getConfirmedBookings();
    int getCancelledBookings();
    int getCompletedBookings();
    

    Map<String, Integer> getBookingStatistics();
    double getBookingSuccessRate();
    String generateBookingReport();
    

    boolean validateBooking(String username, String transportId);
    boolean canCancelBooking(String bookingId);
    boolean canRescheduleBooking(String bookingId);
}