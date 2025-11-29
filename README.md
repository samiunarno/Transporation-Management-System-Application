To Run Directly 
cd src
chmod +x runMac.command/runLinux.sh/runWindows.bat
then
./runMac.command or ./runLinux.sh  or  ./runWindows.bat

Program Will Run Automatically 

🛠️ Installation
Prerequisites
Java JDK 8 or higher

Any Java-compatible IDE (Eclipse, IntelliJ, VS Code)

Basic knowledge of command line operations

Step-by-Step Installation Guide
1. Create Project Structure
bash
# Create main project directory
mkdir TransportationManagementSystem
cd TransportationManagementSystem

# Create source folder structure
mkdir -p src/models src/services

# Navigate to source directory
cd src
2. File Structure Setup
Create the following folder structure and add corresponding Java files:

text
TransportationManagementSystem/
├── src/
│   ├── models/
│   │   ├── User.java
│   │   ├── Transport.java
│   │   ├── AirTransport.java
│   │   ├── SeaTransport.java
│   │   ├── Booking.java
│   │   └── UserLock.java
│   ├── services/
│   │   ├── AuthService.java
│   │   ├── TransportService.java
│   │   ├── BookingService.java
│   │   └── UserLockService.java
│   └── Main.java
3. Compile the Project
bash
# From src directory, compile all Java files
javac -d . *.java models/*.java services/*.java
4. Run the Application
bash
# Run the main class
java Main
5. Verify Installation
If successful, you should see:

text
🚀 Starting Transportation Management System...
🚀 Welcome to Transportation Management System 🚀
Quick Installation (One Command)
bash
mkdir -p TransportationManagementSystem/src/{models,services} && cd TransportationManagementSystem/src
# Then add all Java files and run: javac -d . *.java models/*.java services/*.java && java Main
✨ Features
🔐 Authentication & Security System
Multi-role Login System - Admin, Operator, and Passenger roles

User Registration - New user account creation with role assignment

Secure Session Management - Automatic logout and session handling

Account Lock Protection - Automatic lock after 3 failed login attempts

Admin Unlock System - Special key-based account unlocking

Role-based Permissions - Different access levels for different user types

🚢 Transport Management
Multiple Transport Types - Support for Air and Sea transports

Dynamic Transport Addition - Add new transports with unique IDs

Transport Removal - Delete transports from the system

Real-time Availability - Live tracking of transport availability status

Capacity Management - Set and manage passenger capacity for each transport

Speed Configuration - Configure speed settings for different transport types

Transport Categorization - Filter and view transports by type (AIR/SEA)

📅 Advanced Booking System
Easy Booking Process - Simple transport booking for passengers

Booking History - Complete history of all bookings

Booking Cancellation - Cancel confirmed bookings

Status Tracking - Track booking status (CONFIRMED/CANCELLED/COMPLETED)

Personal Booking Management - Users can view and manage their own bookings

Automatic Availability Update - Transport availability automatically updates on booking

👥 Comprehensive User Management
User Role Management - Admin can view all users and their roles

Failed Login Tracking - Monitor and track failed login attempts

Account Status Monitoring - View active and locked accounts

User Activity Logging - Track user activities within the system

Profile Management - Users can manage their own profiles

🎯 Role-Based Dashboard System
👨‍💼 Admin Dashboard Features
Full system access and control

View all transports and bookings

Add and remove transports

Manage user accounts and roles

Unlock locked user accounts

View comprehensive system statistics

Monitor system health and usage

👷 Operator Dashboard Features
Transport management capabilities

View available transports

Manage booking operations

Check transport status and availability

Limited access to user management

👤 Passenger Dashboard Features
Personal booking management

View available transports

Book available transports

View personal booking history

Cancel own bookings

Limited to personal activities

🔒 Security & Protection Features
Automatic Account Lock - System locks accounts after 3 failed attempts

Unique Unlock Keys - Auto-generated secure unlock keys

Force Unlock Capability - Admin can force unlock any account

Input Validation - Basic validation for user inputs

Error Handling - Comprehensive error handling throughout the system

📊 System Monitoring & Reporting
Real-time Statistics - Live system statistics and metrics

Transport Analytics - View transport counts by type

Booking Analytics - Track confirmed vs cancelled bookings

User Activity Reports - Monitor user engagement and activities

System Health Monitoring - Track overall system performance