#!/bin/bash

# Change to the directory where the script is located
cd "$(dirname "$0")"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
PURPLE='\033[0;35m'
NC='\033[0m' # No Color

clear
echo -e "${PURPLE}"
echo "==============================================="
echo "   Transportation Management System"
echo "        Auto-Compile and Run Script"
echo "              macOS Version"
echo "==============================================="
echo -e "${NC}"

# Check if Java is installed
echo -e "${YELLOW}Checking Java installation...${NC}"
if ! command -v java &> /dev/null; then
    echo -e "${RED}ERROR: Java is not installed!${NC}"
    echo "To install Java:"
    echo "1. Visit https://adoptium.net/"
    echo "2. Download macOS .pkg installer"
    echo "3. Install and restart terminal"
    echo ""
    echo "Or use Homebrew:"
    echo "brew install openjdk"
    exit 1
fi

java_version=$(java -version 2>&1 | head -n 1 | awk -F '"' '{print $2}')
echo -e "${GREEN}Java found! Version: $java_version${NC}"

# Check project structure - FIXED: Check for actual folders, not src/ folders
echo -e "${YELLOW}Checking project structure...${NC}"

if [ ! -d "models" ]; then
    echo -e "${RED}ERROR: 'models' folder not found!${NC}"
    echo "Current directory: $(pwd)"
    echo "Available folders:"
    ls -la
    exit 1
fi

if [ ! -d "services" ]; then
    echo -e "${RED}ERROR: 'services' folder not found!${NC}"
    echo "Current directory: $(pwd)"
    echo "Available folders:"
    ls -la
    exit 1
fi

if [ ! -f "Main.java" ]; then
    echo -e "${RED}ERROR: Main.java not found!${NC}"
    echo "Current directory: $(pwd)"
    echo "Available files:"
    ls -la *.java 2>/dev/null || echo "No Java files found"
    exit 1
fi

echo -e "${GREEN}Project structure verified!${NC}"

# Check if files exist in models and services folders
echo -e "${YELLOW}Checking Java files in folders...${NC}"
if [ $(ls models/*.java 2>/dev/null | wc -l) -eq 0 ]; then
    echo -e "${RED}ERROR: No Java files found in models folder!${NC}"
    exit 1
fi

if [ $(ls services/*.java 2>/dev/null | wc -l) -eq 0 ]; then
    echo -e "${RED}ERROR: No Java files found in services folder!${NC}"
    exit 1
fi

echo -e "${GREEN}All Java files found!${NC}"

# Clean previous compilation
echo -e "${YELLOW}Cleaning previous compilation...${NC}"
find . -name "*.class" -type f -delete 2>/dev/null

# Compile Java files - FIXED: Correct compile command
echo -e "${YELLOW}Compiling Java files...${NC}"
echo "==============================================="

javac -d . Main.java models/*.java services/*.java

if [ $? -ne 0 ]; then
    echo -e "${RED}"
    echo "❌ COMPILATION FAILED!"
    echo "Please check the errors above and fix your code"
    echo -e "${NC}"
    
    # Offer to open in TextEdit for debugging - FIXED: Use correct file paths
    read -p "Open error details in TextEdit? (y/n): " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        # Capture compilation errors and open in TextEdit
        javac -d . Main.java models/*.java services/*.java 2>&1 | tee /tmp/compile_errors.txt
        open -a TextEdit /tmp/compile_errors.txt
    fi
    exit 1
fi

echo -e "${GREEN}"
echo "✅ COMPILATION SUCCESSFUL!"
echo "==============================================="
echo -e "${NC}"

echo -e "${BLUE}Starting Transportation Management System...${NC}"
echo "🚀 Welcome to Transportation Management System 🚀"
echo

sleep 2

# Run the application - FIXED: Run Main directly, not src.Main
echo -e "${YELLOW}Starting application...${NC}"
java Main

echo
echo "==============================================="
echo -e "${GREEN}Program execution completed!${NC}"
echo "You can run this script again to restart the application."

# Keep terminal open
read -p "Press Enter to close this window..." </dev/tty