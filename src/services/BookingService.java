package services;

import interfaces.IBookingService;
import models.Booking;
import models.Transport;
import java.util.*;

public class BookingService implements IBookingService {
    private Map<String, Booking> bookings;
    private TransportService transportService;
    private List<String> bookingLog;

    public BookingService(TransportService transportService) {
        this.bookings = new HashMap<>();
        this.transportService = transportService;
        this.bookingLog = new ArrayList<>();
    }

    @Override
    public Booking createBooking(String username, String transportId) {
        if (!validateBooking(username, transportId)) {
            System.out.println(" Booking validation failed!");
            return null;
        }

        Transport transport = transportService.findTransport(transportId);
        transport.setAvailable(false);
        
        Booking booking = new Booking(username, transportId);
        bookings.put(booking.getBookingId(), booking);
        
        bookingLog.add("Booking created: " + booking.getBookingId() + " by " + username);
        System.out.println("Booking created: " + booking.getBookingId());
        return booking;
    }

    @Override
    public boolean cancelBooking(String bookingId) {
        Booking booking = bookings.get(bookingId);
        
        if (booking == null) {
            System.out.println("Booking not found!");
            return false;
        }
        
        if (!canCancelBooking(bookingId)) {
            System.out.println("Cannot cancel this booking!");
            return false;
        }

        booking.cancel();
        Transport transport = transportService.findTransport(booking.getTransportId());
        if (transport != null) {
            transport.setAvailable(true);
        }
        
        bookingLog.add("Booking cancelled: " + bookingId);
        System.out.println("Booking cancelled: " + bookingId);
        return true;
    }

    @Override
    public boolean confirmBooking(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking != null && booking.getStatus().equals("CONFIRMED")) {
            System.out.println("Booking already confirmed: " + bookingId);
            return true;
        }
        return false;
    }

    @Override
    public boolean completeBooking(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking != null && booking.getStatus().equals("CONFIRMED")) {
            booking.complete();
            bookingLog.add("Booking completed: " + bookingId);
            System.out.println("Booking completed: " + bookingId);
            return true;
        }
        return false;
    }

    @Override
    public boolean rescheduleBooking(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking != null && canRescheduleBooking(bookingId)) {
            booking.reschedule();
            bookingLog.add("Booking rescheduled: " + bookingId);
            System.out.println("Booking rescheduled: " + bookingId);
            return true;
        }
        return false;
    }

    @Override
    public Booking findBookingById(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking == null) {
            System.out.println("Booking not found: " + bookingId);
        }
        return booking;
    }

    @Override
    public List<Booking> getUserBookings(String username) {
        List<Booking> userBookings = new ArrayList<>();
        for (Booking b : bookings.values()) {
            if (b.getUsername().equals(username)) {
                userBookings.add(b);
            }
        }
        return userBookings;
    }

    @Override
    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings.values());
    }

    @Override
    public List<Booking> getTodayBookings() {
        List<Booking> todayBookings = new ArrayList<>();
        java.time.LocalDate today = java.time.LocalDate.now();
        
        for (Booking b : bookings.values()) {
            if (b.getBookingTime().toLocalDate().equals(today)) {
                todayBookings.add(b);
            }
        }
        return todayBookings;
    }

    @Override
    public List<Booking> getPendingBookings() {
        List<Booking> pending = new ArrayList<>();
        for (Booking b : bookings.values()) {
            if (b.getStatus().equals("CONFIRMED")) {
                pending.add(b);
            }
        }
        return pending;
    }

    @Override
    public String getBookingStatus(String bookingId) {
        Booking booking = bookings.get(bookingId);
        return booking != null ? booking.getStatus() : "NOT_FOUND";
    }

    @Override
    public boolean isBookingConfirmed(String bookingId) {
        return getBookingStatus(bookingId).equals("CONFIRMED");
    }

    @Override
    public boolean isBookingCancelled(String bookingId) {
        return getBookingStatus(bookingId).equals("CANCELLED");
    }

    @Override
    public boolean isBookingCompleted(String bookingId) {
        return getBookingStatus(bookingId).equals("COMPLETED");
    }

    @Override
    public int getTotalBookings() {
        return bookings.size();
    }

    @Override
    public int getConfirmedBookings() {
        return (int) bookings.values().stream()
                .filter(b -> b.getStatus().equals("CONFIRMED"))
                .count();
    }

    @Override
    public int getCancelledBookings() {
        return (int) bookings.values().stream()
                .filter(b -> b.getStatus().equals("CANCELLED"))
                .count();
    }

    @Override
    public int getCompletedBookings() {
        return (int) bookings.values().stream()
                .filter(b -> b.getStatus().equals("COMPLETED"))
                .count();
    }

    @Override
    public Map<String, Integer> getBookingStatistics() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("Total", getTotalBookings());
        stats.put("Confirmed", getConfirmedBookings());
        stats.put("Cancelled", getCancelledBookings());
        stats.put("Completed", getCompletedBookings());
        return stats;
    }

    @Override
    public double getBookingSuccessRate() {
        if (getTotalBookings() == 0) return 0.0;
        return (double) getConfirmedBookings() / getTotalBookings() * 100;
    }

    @Override
    public String generateBookingReport() {
        StringBuilder report = new StringBuilder();
        report.append("BOOKING REPORT\n");
        report.append("=".repeat(40)).append("\n");
        report.append("Total Bookings: ").append(getTotalBookings()).append("\n");
        report.append("Confirmed: ").append(getConfirmedBookings()).append("\n");
        report.append("Cancelled: ").append(getCancelledBookings()).append("\n");
        report.append("Completed: ").append(getCompletedBookings()).append("\n");
        report.append("Success Rate: ").append(String.format("%.2f", getBookingSuccessRate())).append("%\n");
        return report.toString();
    }

    @Override
    public boolean validateBooking(String username, String transportId) {
        Transport transport = transportService.findTransport(transportId);
        return transport != null && transport.isAvailable();
    }

    @Override
    public boolean canCancelBooking(String bookingId) {
        Booking booking = bookings.get(bookingId);
        return booking != null && booking.getStatus().equals("CONFIRMED");
    }

    @Override
    public boolean canRescheduleBooking(String bookingId) {
        return canCancelBooking(bookingId);
    }
    
    public void displayAllBookings() {
        System.out.println("\nALL BOOKINGS (" + bookings.size() + " total):");
        System.out.println("=".repeat(60));
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            for (Booking b : bookings.values()) {
                System.out.println(b);
            }
        }
    }
}