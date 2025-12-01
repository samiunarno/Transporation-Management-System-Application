@echo off
chcp 65001 >nul
echo ==============================================
echo    Transportation Management System
echo ==============================================

echo Creating necessary directories...
mkdir data >nul 2>&1
mkdir logs >nul 2>&1

echo Compiling source files...
dir /s /B src\*.java > sources.txt
javac -d . @sources.txt

if %errorlevel% equ 0 (
    echo Compilation Successful!
    echo Starting Application...
    echo ==============================================
    java Main
) else (
    echo Compilation Failed!
    echo Please check the errors above.
)

del sources.txt >nul 2>&1
pause