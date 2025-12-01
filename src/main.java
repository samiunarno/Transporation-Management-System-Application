import services.*;
import interfaces.*;
import models.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Transportation Management System...");
        
        IUserLockService lockService = new UserLockService();
        IAuthService authService = new AuthService();
        ITransportService transportService = new TransportService();
        IBookingService bookingService = new BookingService((TransportService) transportService);
        
        TransportationSystem system = new TransportationSystem(
            authService, transportService, bookingService, lockService);
        
        system.start();
    }
}

class TransportationSystem {
    private IAuthService authService;
    private ITransportService transportService;
    private IBookingService bookingService;
    private IUserLockService lockService;
    private Scanner scanner;
    
    public TransportationSystem(IAuthService authService, ITransportService transportService,
                              IBookingService bookingService, IUserLockService lockService) {
        this.authService = authService;
        this.transportService = transportService;
        this.bookingService = bookingService;
        this.lockService = lockService;
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        System.out.println("Welcome to Transportation Management System");
        
        while (true) {
            if (!authService.isLoggedIn()) {
                showLoginMenu();
            } else {
                showDashboard();
            }
        }
    }
    
    private void showLoginMenu() {
        System.out.println("\n==================================================");
        System.out.println("MAIN MENU");
        System.out.println("==================================================");
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
        
        System.out.println("\n============================================================");
        System.out.println("DASHBOARD - Welcome, " + currentUser.getUsername() + " (" + role + ")");
        System.out.println("============================================================");
        
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
        List<Transport> transports = transportService.getAllTransports();
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
        System.out.println("Transport " + id + " added successfully!");
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
            System.out.println("Booking successful! Booking ID: " + booking.getBookingId());
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
        List<Transport> allTransports = transportService.getAllTransports();
        long available = allTransports.stream().filter(Transport::isAvailable).count();
        long busy = allTransports.size() - available;
        
        System.out.println("Total Transports: " + allTransports.size());
        System.out.println("Available: " + available);
        System.out.println("Busy: " + busy);
    }
    
    private void showStatistics() {
        System.out.println("\nSYSTEM STATISTICS");
        
        int totalTransports = transportService.getTotalTransports();
        int availableTransports = transportService.getAvailableCount();
        
        System.out.println("Transport Statistics:");
        System.out.println("  - Total Transports: " + totalTransports);
        System.out.println("  - Available: " + availableTransports);
        System.out.println("  - In Use: " + (totalTransports - availableTransports));
        
        int totalBookings = bookingService.getTotalBookings();
        int confirmedBookings = bookingService.getConfirmedBookings();
        int cancelledBookings = bookingService.getCancelledBookings();
        int completedBookings = bookingService.getCompletedBookings();
        
        System.out.println("Booking Statistics:");
        System.out.println("  - Total Bookings: " + totalBookings);
        System.out.println("  - Confirmed: " + confirmedBookings);
        System.out.println("  - Cancelled: " + cancelledBookings);
        System.out.println("  - Completed: " + completedBookings);
        
        int lockedUsers = lockService.getLockedUsersCount();
        System.out.println("User Statistics:");
        System.out.println("  - Locked Accounts: " + lockedUsers);
        
        if (totalBookings > 0) {
            double successRate = bookingService.getBookingSuccessRate();
            System.out.println("Booking Success Rate: " + String.format("%.2f", successRate) + "%");
        }
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
            System.out.println("User: " + lock.getUsername() + " | Locked By: " + lock.getLockedBy() + " | Key: " + lock.getUnlockKey());
        }
        
        System.out.println("\n1. Unlock with Key");
        System.out.println("2. Force Unlock (Admin Power)");
        System.out.println("3. Display Lock Details");
        System.out.println("4. Back");
        System.out.print("Choose option: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                unlockWithKey();
                break;
            case 2:
                forceUnlock();
                break;
            case 3:
                displayLockDetails();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid option!");
        }
    }
    
    private void unlockWithKey() {
        System.out.print("Enter username to unlock: ");
        String username = scanner.nextLine();
        System.out.print("Enter unlock key: ");
        String key = scanner.nextLine();
        
        if (lockService.unlockUser(username, key)) {
            System.out.println("Account unlocked successfully!");
        } else {
            System.out.println("Unlock failed! Invalid key or username.");
        }
    }
    
    private void forceUnlock() {
        System.out.print("Enter username to force unlock: ");
        String username = scanner.nextLine();
        
        if (lockService.unlockWithAdminPower(username)) {
            System.out.println("Account force unlocked successfully!");
        } else {
            System.out.println("User not found or not locked!");
        }
    }
    
    private void displayLockDetails() {
        System.out.print("Enter username to view lock details: ");
        String username = scanner.nextLine();
        
        UserLock lock = lockService.getLockInfo(username);
        if (lock != null) {
            System.out.println("\nLOCK DETAILS:");
            System.out.println("Username: " + lock.getUsername());
            System.out.println("Locked By: " + lock.getLockedBy());
            System.out.println("Locked At: " + lock.getLockedAt());
            System.out.println("Unlock Key: " + lock.getUnlockKey());
            System.out.println("Duration: " + lock.getLockDuration());
        } else {
            System.out.println("User not locked!");
        }
    }
    
    private void displayAllUsers() {
        System.out.println("\nALL USERS");
        Map<String, User> users = authService.getUsers();
        
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            for (User user : users.values()) {
                String lockStatus = lockService.isUserLocked(user.getUsername()) ? "[LOCKED]" : "[ACTIVE]";
                String activeStatus = user.isActive() ? "Active" : "Inactive";
                System.out.println(lockStatus + " " + user.getUsername() + 
                    " (Role: " + user.getRole() + ", Status: " + activeStatus + 
                    ", Failed Attempts: " + user.getFailedLoginAttempts() + ")");
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