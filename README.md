A comprehensive Java-based Transportation Management System that provides role-based access control for managing air and sea transports, bookings, and user accounts with advanced security features.

https://img.shields.io/badge/Java-17-blue
https://img.shields.io/badge/OOP-Design-orange
https://img.shields.io/badge/License-MIT-green

📋 Table of Contents
Features

System Architecture

Installation

Usage

User Roles

Class Diagram

API Documentation

Contributing

License

✨ Features
🔐 Authentication & Security
Role-based Access Control (Admin, Operator, Passenger)

User Registration & Login system

Account Locking after 3 failed login attempts

Admin-controlled Account Unlocking with special keys

Session Management with secure logout

🚢 Transport Management
Multi-type Transport Support (Air & Sea)

Real-time Availability tracking

Capacity & Speed Management

Add/Remove Transport functionality

Transport Status Monitoring

📅 Booking System
Easy Transport Booking for passengers

Booking History tracking

Cancel Booking functionality

Booking Status management (Confirmed/Cancelled/Completed)

👥 User Management
User Role Management by admin

Failed Login Attempt tracking

Account Lock/Unlock system

User Activity Monitoring

🏗️ System Architecture
text
TransportationManagementSystem/
├── src/
│   ├── models/          # Data Models
│   │   ├── User.java
│   │   ├── Transport.java
│   │   ├── AirTransport.java
│   │   ├── SeaTransport.java
│   │   ├── Booking.java
│   │   └── UserLock.java
│   ├── services/        # Business Logic
│   │   ├── AuthService.java
│   │   ├── TransportService.java
│   │   ├── BookingService.java
│   │   └── UserLockService.java
│   └── Main.java        # Application Entry Point
🛠️ Installation
Prerequisites
Java JDK 8 or higher

Any Java-compatible IDE (Eclipse, IntelliJ, VS Code)

Step-by-Step Setup
Clone the Repository

bash
git clone https://github.com/yourusername/transportation-management-system.git
cd transportation-management-system
Create Project Structure

bash
mkdir -p src/models src/services
Copy Source Files

Copy all .java files to their respective directories

Ensure the package structure is maintained

Compile the Project

bash
cd src
javac -d . *.java models/*.java services/*.java
Run the Application

bash
java Main
🎮 Usage
Starting the Application
bash
java Main
Default Login Credentials
Role	Username	Password	Access Level
👨‍💼 Admin	admin	admin123	Full system access
👷 Operator	operator	op123	Transport & booking management
👤 Passenger	john	pass123	Personal booking management
Demo User Accounts for Testing
Admin: Full system control

Operator: Try wrong password 3 times to test lock feature

Passenger: Book transports and manage personal bookings

👥 User Roles & Permissions
🎯 Admin
✅ View all transports and bookings

✅ Add/Remove transports

✅ Manage user accounts

✅ Unlock locked accounts

✅ View system statistics

✅ Access all system features

🔧 Operator
✅ View available transports

✅ Manage bookings

✅ Check transport status

❌ Cannot manage users

❌ Cannot unlock accounts

👤 Passenger
✅ View available transports

✅ Book transports

✅ View personal bookings

✅ Cancel own bookings

❌ Cannot manage system

🔒 Security Features
Account Locking System
java
// After 3 failed login attempts
if (user.getFailedLoginAttempts() >= 3) {
    lockService.lockUser(username, "SYSTEM");
    System.out.println("🚫 Account locked!");
}
Special Unlock Keys
Auto-generated unique unlock keys

Admin can unlock with keys or force unlock

Secure account recovery system

📊 Class Diagram
text
User ──────┐
           ├── AuthService
UserLock ──┘
                   │
Transport ───────┐ │
    ├──────────┐ ├── TransportService
AirTransport  │ │
SeaTransport ─┘ │
                ├── BookingService
Booking ────────┘
Core Classes Overview
User: Manages user credentials and roles

Transport: Abstract base class for all transports

AirTransport: Specialized air transport functionality

SeaTransport: Specialized sea transport functionality

Booking: Manages transport bookings

UserLock: Handles account locking mechanism

🔄 API Reference
AuthService Methods
java
boolean login(String username, String password)
boolean register(String username, String password, String email, String role)
void logout()
boolean isLoggedIn()
TransportService Methods
java
void addTransport(Transport transport)
boolean removeTransport(String id)
List<Transport> getAvailableTransports()
List<Transport> getTransportsByType(String type)
BookingService Methods
java
Booking createBooking(String username, String transportId)
boolean cancelBooking(String bookingId)
List<Booking> getUserBookings(String username)
🧪 Testing the System
Testing Account Lock Feature
Login as operator with wrong password 3 times

Account will be automatically locked

Login as admin and unlock the account

Use the generated unlock key or force unlock

Sample Test Flow
bash
# 1. Login as admin
Username: admin
Password: admin123

# 2. Add new transport
Transport Type: AIR
Name: Boeing 747
Capacity: 300
Speed: 900
Airline: Test Airlines

# 3. Login as passenger and book transport
Username: john
Password: pass123
🚀 Future Enhancements
Database integration for persistent storage

Web-based GUI interface

Email notifications for bookings

Payment integration

Mobile application

Real-time transport tracking

Advanced reporting system

🤝 Contributing
We welcome contributions! Please follow these steps:

Fork the project

Create your feature branch (git checkout -b feature/AmazingFeature)

Commit your changes (git commit -m 'Add some AmazingFeature')

Push to the branch (git push origin feature/AmazingFeature)

Open a Pull Request

Development Guidelines
Follow Java coding conventions

Write meaningful commit messages

Add comments for complex logic

Test all features before submitting

📝 License
This project is licensed under the MIT License - see the LICENSE file for details.

👨‍💻 Authors
Your Name - Initial work - YourGitHub

🙏 Acknowledgments
Java Platform for robust backend

OOP principles for clean architecture

All contributors who helped shape this project

<div align="center">
⭐ Don't forget to star this repository if you find it helpful!
Happy Coding! 🚀

</div>
📞 Support
If you have any questions or need help with setup:

Create an Issue

Email: your.email@domain.com