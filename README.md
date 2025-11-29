
# ✈️🚢 Transportation Management System 🚢✈️

<p align="center">
  <img src="https://img.shields.io/badge/Java-JDK%208%2B-blue?style=for-the-badge&logo=java" alt="Java Version">
  <img src="https://img.shields.io/badge/Platform-CLI-lightgrey?style=for-the-badge&logo=powershell" alt="Platform">
  <img src="https://img.shields.io/badge/Status-Active-brightgreen?style=for-the-badge" alt="Status">
</p>

<p align="center">
  A robust, secure, and feature-rich command-line application for managing air and sea transportation, bookings, and users with a sophisticated role-based access system.
</p>

---

## ✨ Key Features

### 🔐 Authentication & Advanced Security
-   **Multi-role Login System**: Differentiated access for `Admin`, `Operator`, and `Passenger` roles.
-   **Secure User Registration**: New users can register with a specific role.
-   **Strong Password Policy**: Enforces robust password requirements (length, case, numbers, special characters).
-   **Account Lock Protection**: Automatically locks an account after 3 consecutive failed login attempts.
-   **Admin Unlock System**: Admins can unlock accounts using a unique, auto-generated secure key.
-   **Role-based Permissions**: Granular control over what each user role can see and do.

### 🚢 Comprehensive Transport Management
-   **Multiple Transport Types**: Full support for both **Air** and **Sea** transport logistics.
-   **Dynamic Fleet Management**: Admins can add or remove transport options on-the-fly.
-   **Real-time Availability**: Live status tracking for all transports.
-   **Capacity & Speed Configuration**: Easily set and manage passenger capacity and speed for each transport.
-   **Easy Filtering**: View and categorize transports by type (`AIR`/`SEA`).

### 📅 Advanced Booking System
-   **Effortless Booking**: A streamlined, console-based process for passengers to book transports.
-   **Complete Booking History**: Access a comprehensive log of all bookings in the system.
-   **Flexible Cancellation**: Users can cancel their confirmed bookings.
-   **Live Status Tracking**: Monitor booking status (`CONFIRMED`/`CANCELLED`/`COMPLETED`).
-   **Automatic Availability Sync**: Transport availability is instantly updated after a booking or cancellation.

### 🎯 Role-Based Dashboards & User Management
-   **Admin Dashboard (`👨‍💼`)**: Full system oversight, user management, and transport configuration.
-   **Operator Dashboard (`👷`)**: Manage transport operations, view bookings, and check transport status.
-   **Passenger Dashboard (`👤`)**: Manage personal bookings, view transport availability, and cancel trips.
-   **Central User Management**: Admins can view all users, their roles, and account status (active/locked).

### 📊 System Monitoring & Reporting
-   **Real-time Statistics**: Get a live snapshot of system metrics.
-   **Transport Analytics**: View transport counts categorized by type.
-   **Booking Analytics**: Track the ratio of confirmed vs. cancelled bookings.
-   **User Activity Reports**: Monitor user engagement and system-wide activities.

---

## 📂 Project Structure
```
TransportationManagementSystem/
└── src/
    ├── models/
    │   ├── User.java
    │   ├── Transport.java
    │   ├── AirTransport.java
    │   ├── SeaTransport.java
    │   ├── Booking.java
    │   └── UserLock.java
    ├── services/
    │   ├── AuthService.java
    │   ├── TransportService.java
    │   ├── BookingService.java
    │   └── UserLockService.java
    └── Main.java
```

---

## 🛠️ Installation & Setup

### Prerequisites
-   Java JDK 8 or higher.
-   A Java-compatible IDE (e.g., Eclipse, IntelliJ IDEA, VS Code).
-   Basic knowledge of command-line operations.

### Step-by-Step Guide
1.  **Create Project Directory:**
    ```bash
    mkdir TransportationManagementSystem
    cd TransportationManagementSystem
    ```
2.  **Create Source Structure:**
    ```bash
    mkdir -p src/models src/services
    ```
3.  **Add Java Files:** Place all `.java` files into their corresponding folders as shown in the project structure.

4.  **Compile the Project:** Navigate to the `src` directory and run:
    ```bash
    javac -d . *.java models/*.java services/*.java
    ```

### Quick Installation (One Command)
For a rapid setup, navigate to your desired projects folder and run:
```bash
mkdir -p TransportationManagementSystem/src/{models,services} && cd TransportationManagementSystem/src
# Add all your Java files to the correct folders, then run:
# javac -d . *.java models/*.java services/*.java && java Main
```

---

## 🚀 Running the Application

After successful compilation, run the application from within the **`src`** directory:
```bash
java Main
```
If successful, you will see the welcome message:
```
🚀 Starting Transportation Management System...
🚀 Welcome to Transportation Management System 🚀
```

---

## ☕ Contact

Feel free to have a coffee, I mean **contact me**! If you have any questions, suggestions, or feedback, please don't hesitate to reach out.
