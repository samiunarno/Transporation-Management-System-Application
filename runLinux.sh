#!/bin/bash

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}"
echo "==============================================="
echo "   Transportation Management System"
echo "        Auto-Compile and Run Script"
echo "==============================================="
echo -e "${NC}"

# Check if Java is installed
echo -e "${YELLOW}Checking Java installation...${NC}"
if ! command -v java &> /dev/null; then
    echo -e "${RED}ERROR: Java is not installed or not in PATH!${NC}"
    echo "Please install Java JDK 8 or higher:"
    echo "Ubuntu/Debian: sudo apt install openjdk-11-jdk"
    echo "CentOS/RHEL: sudo yum install java-11-openjdk"
    exit 1
fi

echo -e "${GREEN}Java found! $(java -version 2>&1 | head -n 1)${NC}"

# Check project structure
echo -e "${YELLOW}Checking project structure...${NC}"

if [ ! -d "models" ]; then
    echo -e "${RED}ERROR: 'models' folder not found!${NC}"
    echo "Please ensure you're in the correct directory"
    exit 1
fi

if [ ! -d "services" ]; then
    echo -e "${RED}ERROR: 'services' folder not found!${NC}"
    echo "Please ensure you're in the correct directory"
    exit 1
fi

echo -e "${GREEN}Project structure verified!${NC}"

# Clean previous compilation
echo -e "${YELLOW}Cleaning previous compilation...${NC}"
find . -name "*.class" -type f -delete

# Compile Java files
echo -e "${YELLOW}Compiling Java files...${NC}"
echo "==============================================="

javac -d . Main.java models/*.java services/*.java

if [ $? -ne 0 ]; then
    echo -e "${RED}"
    echo "❌ COMPILATION FAILED!"
    echo "Please check the errors above and fix your code"
    echo -e "${NC}"
    exit 1
fi

echo -e "${GREEN}"
echo "✅ COMPILATION SUCCESSFUL!"
echo "==============================================="
echo -e "${NC}"

echo -e "${BLUE}Starting Transportation Management System...${NC}"
echo

sleep 2

# Run the application
java Main

echo
echo "==============================================="
echo -e "${GREEN}Program execution completed!${NC}"