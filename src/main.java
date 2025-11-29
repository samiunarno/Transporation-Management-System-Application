import services.*;
import models.*;
import java.util.*;

public class main {
    public static void main(String[] args) {
        System.out.println("Starting Transportation Management System...");
        
        AuthService authService = new AuthService();
        TransportService transportService = new TransportService();
        BookingService bookingService = new BookingService(transportService);
        UserLockService lockService = authService.getLockService();
        
        TransportationSystem system = new TransportationSystem(
            authService, transportService, bookingService, lockService);
        
        system.start();
    }
}

class TransportationSystem {
    private AuthService authService;
    private TransportService transportService;
    private BookingService bookingService;
    private UserLockService lockService;
    private Scanner scanner;
    
    public TransportationSystem(AuthService authService, TransportService transportService,
                              BookingService bookingService, UserLockService lockService) {
        this.authService = authService;
        this.transportService = transportService;
        this.bookingService = bookingService;
        this.lockService = lockService;
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        System.out.println("Welcome to Transportation Management System ");
        
        while (true) {
            if (!authService.isLoggedIn()) {
                showLoginMenu();
            } else {
                showDashboard();
            }
        }
    }
    
    private void showLoginMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose option: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                System.out.println("Thank you for using our system!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option!");
        }
    }
    
    private void login() {
        System.out.println("\nLOGIN");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        authService.login(username, password);
    }
    
    private void register() {
        System.out.println("\nREGISTER");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Role (ADMIN/OPERATOR/PASSENGER): ");
        String role = scanner.nextLine().toUpperCase();
        
        if (!role.equals("ADMIN") && !role.equals("OPERATOR") && !role.equals("PASSENGER")) {
            System.out.println("Invalid role! Use ADMIN, OPERATOR or PASSENGER.");
            return;
        }
        
        if (authService.register(username, password, email, role)) {
            System.out.println("Registration successful! Please login.");
        } else {
            System.out.println("Registration failed! Username already exists.");
        }
    }
    
    private void showDashboard() {
        User currentUser = authService.getCurrentUser();
        String role = currentUser.getRole();
        
        System.out.println("\n" + "=".repeat(60));
        System.out.printf("DASHBOARD - Welcome, %s (%s)%n", currentUser.getUsername(), role);
        System.out.println("=".repeat(60));
        
        switch (role) {
            case "ADMIN":
                showAdminDashboard();
                break;
            case "OPERATOR":
                showOperatorDashboard();
                break;
            case "PASSENGER":
                showPassengerDashboard();
                break;
            default:
                System.out.println("Unknown role!");
        }
    }
    
    private void showAdminDashboard() {
        while (authService.isLoggedIn()) {
            System.out.println("\nADMIN DASHBOARD");
            System.out.println("1. View All Transports");
            System.out.println("2. Add New Transport");
            System.out.println("3. Remove Transport");
            System.out.println("4. View All Bookings");
            System.out.println("5. View System Statistics");
            System.out.println("6. Manage Locked Accounts");
            System.out.println("7. View All Users");
            System.out.println("8. Logout");
            System.out.print("Choose option: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    displayAllTransports();
                    break;
                case 2:
                    addNewTransport();
                    break;
                case 3:
                    removeTransport();
                    break;
                case 4:
                    displayAllBookings();
                    break;
                case 5:
                    showStatistics();
                    break;
                case 6:
                    manageLockedAccounts();
                    break;
                case 7:
                    displayAllUsers();
                    break;
                case 8:
                    authService.logout();
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
    
    private void showOperatorDashboard() {
        while (authService.isLoggedIn()) {
            System.out.println("\nOPERATOR DASHBOARD");
            System.out.println("1. View Available Transports");
            System.out.println("2. Manage Bookings");
            System.out.println("3. View Transport Status");
            System.out.println("4. Logout");
            System.out.print("Choose option: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    displayAvailableTransports();
                    break;
                case 2:
                    manageBookings();
                    break;
                case 3:
                    displayTransportStatus();
                    break;
                case 4:
                    authService.logout();
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
    
    private void showPassengerDashboard() {
        while (authService.isLoggedIn()) {
            System.out.println("\nPASSENGER DASHBOARD");
            System.out.println("1. View Available Transports");
            System.out.println("2. Book Transport");
            System.out.println("3. My Bookings");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Logout");
            System.out.print("Choose option: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    displayAvailableTransports();
                    break;
                case 2:
                    bookTransport();
                    break;
                case 3:
                    displayMyBookings();
                    break;
                case 4:
                    cancelMyBooking();
                    break;
                case 5:
                    authService.logout();
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
    
    private void displayAllTransports() {
        System.out.println("\nALL TRANSPORTS");
        List<Transport> transports = transportService.getAvailableTransports();
        if (transports.isEmpty()) {
            System.out.println("No transports available.");
        } else {
            for (Transport transport : transports) {
                transport.displayInfo();
            }
        }
    }
    
    private void displayAvailableTransports() {
        System.out.println("\nAVAILABLE TRANSPORTS");
        List<Transport> transports = transportService.getAvailableTransports();
        if (transports.isEmpty()) {
            System.out.println("No available transports at the moment.");
        } else {
            for (Transport transport : transports) {
                transport.displayInfo();
            }
        }
    }
    
    private void addNewTransport() {
        System.out.println("\nADD NEW TRANSPORT");
        System.out.print("Transport Type (AIR/SEA): ");
        String type = scanner.nextLine().toUpperCase();
        
        if (!type.equals("AIR") && !type.equals("SEA")) {
            System.out.println("Invalid transport type!");
            return;
        }
        
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Capacity: ");
        int capacity = getIntInput();
        System.out.print("Speed: ");
        double speed = getDoubleInput();
        
        String id = transportService.generateId(type);
        Transport transport;
        
        if (type.equals("AIR")) {
            System.out.print("Airline: ");
            String airline = scanner.nextLine();
            transport = new AirTransport(id, name, capacity, speed, airline);
        } else {
            System.out.print("Port: ");
            String port = scanner.nextLine();
            transport = new SeaTransport(id, name, capacity, speed, port);
        }
        
        transportService.addTransport(transport);
        System.out.printf("Transport %s added successfully!%n", id);
    }
    
    private void removeTransport() {
        System.out.println("\nREMOVE TRANSPORT");
        displayAllTransports();
        System.out.print("Enter Transport ID to remove: ");
        String id = scanner.nextLine();
        
        if (transportService.removeTransport(id)) {
            System.out.println("Transport removed successfully!");
        } else {
            System.out.println("Transport not found!");
        }
    }
    
    private void bookTransport() {
        System.out.println("\nBOOK TRANSPORT");
        displayAvailableTransports();
        System.out.print("Enter Transport ID to book: ");
        String transportId = scanner.nextLine();
        
        String username = authService.getCurrentUser().getUsername();
        Booking booking = bookingService.createBooking(username, transportId);
        
        if (booking != null) {
            System.out.printf("Booking successful! Booking ID: %s%n", booking.getBookingId());
        } else {
            System.out.println("Booking failed! Transport not available.");
        }
    }
    
    private void displayMyBookings() {
        String username = authService.getCurrentUser().getUsername();
        List<Booking> bookings = bookingService.getUserBookings(username);
        
        System.out.println("\nMY BOOKINGS");
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            for (Booking booking : bookings) {
                System.out.println(booking);
            }
        }
    }
    
    private void cancelMyBooking() {
        displayMyBookings();
        System.out.print("Enter Booking ID to cancel: ");
        String bookingId = scanner.nextLine();
        
        if (bookingService.cancelBooking(bookingId)) {
            System.out.println("Booking cancelled successfully!");
        } else {
            System.out.println("Cancellation failed! Booking not found or already cancelled.");
        }
    }
    
    private void displayAllBookings() {
        System.out.println("\nALL BOOKINGS");
        List<Booking> bookings = bookingService.getAllBookings();
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            for (Booking booking : bookings) {
                System.out.println(booking);
            }
        }
    }
    
    private void manageBookings() {
        System.out.println("\nMANAGE BOOKINGS");
        displayAllBookings();
        System.out.println("1. Cancel Booking");
        System.out.println("2. Back");
        System.out.print("Choose option: ");
        
        int choice = getIntInput();
        if (choice == 1) {
            System.out.print("Enter Booking ID to cancel: ");
            String bookingId = scanner.nextLine();
            
            if (bookingService.cancelBooking(bookingId)) {
                System.out.println("Booking cancelled successfully!");
            } else {
                System.out.println("Cancellation failed!");
            }
        }
    }
    
    private void displayTransportStatus() {
        System.out.println("\nTRANSPORT STATUS");
        List<Transport> allTransports = transportService.getAvailableTransports();
        long available = allTransports.stream().filter(Transport::isAvailable).count();
        long busy = allTransports.size() - available;
        
        System.out.printf("Total Transports: %d%n", allTransports.size());
        System.out.printf("Available: %d%n", available);
        System.out.printf("Busy: %d%n", busy);
    }
    
    private void showStatistics() {
        System.out.println("\nSYSTEM STATISTICS");
        List<Transport> transports = transportService.getAvailableTransports();
        List<Booking> bookings = bookingService.getAllBookings();
        
        long airTransports = transports.stream()
            .filter(t -> t.getType().equals("AIR")).count();
        long seaTransports = transports.stream()
            .filter(t -> t.getType().equals("SEA")).count();
        
        long confirmedBookings = bookings.stream()
            .filter(b -> b.getStatus().equals("CONFIRMED")).count();
        long cancelledBookings = bookings.stream()
            .filter(b -> b.getStatus().equals("CANCELLED")).count();
        
        System.out.printf("Total Transports: %d%n", transports.size());
        System.out.printf("  - Air Transports: %d%n", airTransports);
        System.out.printf("  - Sea Transports: %d%n", seaTransports);
        System.out.printf("Total Bookings: %d%n", bookings.size());
        System.out.printf("  - Confirmed: %d%n", confirmedBookings);
        System.out.printf("  - Cancelled: %d%n", cancelledBookings);
        System.out.printf("Locked Accounts: %d%n", lockService.getAllLockedUsers().size());
    }
    
    private void manageLockedAccounts() {
        System.out.println("\nLOCKED ACCOUNTS MANAGEMENT");
        List<UserLock> lockedUsers = lockService.getAllLockedUsers();
        
        if (lockedUsers.isEmpty()) {
            System.out.println("No locked accounts found.");
            return;
        }
        
        System.out.println("Locked Accounts:");
        for (UserLock lock : lockedUsers) {
            System.out.printf("User: %s | Locked By: %s | Key: %s%n",
                             lock.getUsername(), lock.getLockedBy(), lock.getUnlockKey());
        }
        
        System.out.println("\n1. Unlock with Key");
        System.out.println("2. Force Unlock (Admin Power)");
        System.out.println("3. Back");
        System.out.print("Choose option: ");
        
        int choice = getIntInput();
        if (choice == 1) {
            System.out.print("Enter username to unlock: ");
            String username = scanner.nextLine();
            System.out.print("Enter unlock key: ");
            String key = scanner.nextLine();
            
            if (lockService.lockUser(username, key)) {
                System.out.println("Account unlocked successfully!");
            } else {
                System.out.println("Unlock failed! Invalid key or username.");
            }
        } else if (choice == 2) {
            System.out.print("Enter username to force unlock: ");
            String username = scanner.nextLine();
            
            if (lockService.unlockWithAdminPower(username)) {
                System.out.println("Account force unlocked successfully!");
            } else {
                System.out.println("User not found or not locked!");
            }
        }
    }
    
    private void displayAllUsers() {
        System.out.println("\nALL USERS");
        Map<String, User> users = authService.getUsers();
        
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            for (User user : users.values()) {
                String lockStatus = lockService.isUserLocked(user.getUsername()) ? "" : "";
                System.out.printf("%s %s (Role: %s, Failed Attempts: %d)%n",
                                lockStatus, user.getUsername(), user.getRole(), 
                                user.getFailedLoginAttempts());
            }
        }
    }
    
    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
    
    private double getDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}