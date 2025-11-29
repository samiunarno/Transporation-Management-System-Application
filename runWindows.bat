@echo off
chcp 65001 >nul
title Transportation Management System
color 0A

echo ===============================================
echo    Transportation Management System
echo         Auto-Compile and Run Script
echo ===============================================
echo.

echo Checking Java installation...
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java is not installed or not in PATH!
    echo Please install Java JDK 8 or higher
    pause
    exit /b 1
)

echo Java found! Checking project structure...

if not exist "models" (
    echo ERROR: 'models' folder not found!
    echo Please ensure you're in the correct directory
    pause
    exit /b 1
)

if not exist "services" (
    echo ERROR: 'services' folder not found!
    echo Please ensure you're in the correct directory
    pause
    exit /b 1
)

echo Project structure verified!
echo.
echo Cleaning previous compilation...
if exist "*.class" del /Q *.class >nul 2>&1
if exist "models\*.class" del /Q models\*.class >nul 2>&1
if exist "services\*.class" del /Q services\*.class >nul 2>&1

echo Compiling Java files...
echo ===============================================

javac -encoding UTF-8 -d . Main.java models/*.java services/*.java

if errorlevel 1 (
    echo.
    echo ❌ COMPILATION FAILED!
    echo Please check the errors above and fix your code
    pause
    exit /b 1
)

echo.
echo ✅ COMPILATION SUCCESSFUL!
echo ===============================================
echo Starting Transportation Management System...
echo.

timeout /t 2 /nobreak >nul

java Main

echo.
echo ===============================================
echo Program execution completed!
pause