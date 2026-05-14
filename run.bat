@echo off
REM Batch file to run the Agriculture Management System

echo.
echo ╔════════════════════════════════════════════════════╗
echo ║   RUNNING Agriculture Management System            ║
echo ╚════════════════════════════════════════════════════╝
echo.

cd /d "%~dp0src"

echo [Step 1] Checking if compiled files exist...
if not exist "com\agriculture\Main.class" (
    echo.
    echo ✗ Compiled files not found! Please run compile.bat first.
    cd /d "%~dp0"
    exit /b 1
)

echo [Step 2] Running the application...
echo.
java -cp ".;..\lib\*" com.agriculture.Main

cd /d "%~dp0"
