@echo off
REM Batch file to compile the Agriculture Management System

echo.
echo ╔════════════════════════════════════════════════════╗
echo ║   COMPILING Agriculture Management System          ║
echo ╚════════════════════════════════════════════════════╝
echo.

cd /d "%~dp0src"

echo [Step 0] Cleaning old class files...
for /R %%f in (*.class) do del /Q "%%f"

echo [Step 1] Compiling all Java files...
javac -cp ".;..\lib\*" com\agriculture\*.java ^
       com\agriculture\database\*.java ^
       com\agriculture\models\*.java ^
       com\agriculture\dao\*.java ^
       com\agriculture\ui\*.java

if %errorlevel% neq 0 (
    echo.
    echo ✗ Compilation failed!
    cd /d "%~dp0"
    exit /b 1
)

echo.
echo ✓ Compilation successful!
echo.
cd /d "%~dp0"
pause
