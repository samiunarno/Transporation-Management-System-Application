#!/bin/bash
cd "$(dirname "$0")"

echo "=============================================="
echo "   Transportation Management System"
echo "=============================================="

echo "Creating necessary directories..."
mkdir -p data logs

echo "Compiling source files..."
find src -name "*.java" > sources.txt
javac -d . @sources.txt

if [ $? -eq 0 ]; then
    echo "Compilation Successful!"
    echo "Starting Application..."
    echo "=============================================="
    java Main
else
    echo "Compilation Failed!"
    echo "Please check the errors above."
fi

rm -f sources.txt