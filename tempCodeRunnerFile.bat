@echo off
REM Batch file to compile and run the Agriculture Management System

echo.
echo ╔════════════════════════════════════════════════════╗
echo ║   Agriculture Management System - Build & Run      ║
echo ╚════════════════════════════════════════════════════╝
echo.

cd /d "%~dp0src"

echo [Step 1] Compiling all Java files...
javac -cp ".;..\lib\*" com\agriculture\*.java ^
       com\agriculture\database\*.java ^
       com\agriculture\models\*.java ^
       com\agriculture\dao\*.java ^
       com\agriculture\ui\*.java

if %errorlevel% neq 0 (
    echo.
    echo ✗ Compilation failed!