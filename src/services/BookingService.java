package services;

import models.Booking;
import models.Transport;
import java.util.*;

public class BookingService {

    private Map<String, Booking> bookings;
    private TransportService transportService;

    public BookingService(TransportService transportService) {
        this.bookings = new HashMap<>();
        this.transportService = transportService;
    }

    public Booking createBooking(String username, String transportId) {
        Transport transport = transportService.findTransport(transportId);

        if (transport == null || !transport.isAvailable()) {
            return null;
        }

        transport.setAvailable(false);
        Booking booking = new Booking(username, transportId);
        bookings.put(booking.getBookingId(), booking);

        return booking;
    }

    public boolean cancelBooking(String bookingId) {
        Booking booking = bookings.get(bookingId);

        if (booking != null && booking.getStatus().equals("CONFIRMED")) {
            booking.cancel();
            Transport transport = transportService.findTransport(booking.getTransportId());
            if (transport != null) {
                transport.setAvailable(true);
            }
            return true;
        }
        return false;
    }

    public List<Booking> getUserBookings(String username) {
        List<Booking> list = new ArrayList<>();
        for (Booking b : bookings.values()) {
            if (b.getUsername().equals(username)) {
                list.add(b);
            }
        }
        return list;
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings.values());
    }

    public int getTotalBookings() {
        return bookings.size();
    }

    public int getConfirmedBookings() {
        int count = 0;
        for (Booking b : bookings.values()) {
            if (b.getStatus().equals("CONFIRMED")) {
                count++;
            }
        }
        return count;
    }
}
