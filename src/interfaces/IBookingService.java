package interfaces;

import models.Booking;
import java.util.List;
import java.util.Map;

public interface IBookingService {
    // Booking Operations
    Booking createBooking(String username, String transportId);
    boolean cancelBooking(String bookingId);
    boolean confirmBooking(String bookingId);
    boolean completeBooking(String bookingId);
    boolean rescheduleBooking(String bookingId);
    
    // Booking Retrieval
    Booking findBookingById(String bookingId);
    List<Booking> getUserBookings(String username);
    List<Booking> getAllBookings();
    List<Booking> getTodayBookings();
    List<Booking> getPendingBookings();
    
    // Booking Status
    String getBookingStatus(String bookingId);
    boolean isBookingConfirmed(String bookingId);
    boolean isBookingCancelled(String bookingId);
    boolean isBookingCompleted(String bookingId);
    
    // Booking Information
    int getTotalBookings();
    int getConfirmedBookings();
    int getCancelledBookings();
    int getCompletedBookings();
    
    // Statistics & Reports
    Map<String, Integer> getBookingStatistics();
    double getBookingSuccessRate();
    String generateBookingReport();
    
    // Validation
    boolean validateBooking(String username, String transportId);
    boolean canCancelBooking(String bookingId);
    boolean canRescheduleBooking(String bookingId);
}