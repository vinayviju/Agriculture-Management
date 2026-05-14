# 🚀 Agriculture Management System - Setup Guide

## Prerequisites
✓ Java JDK 8 or higher installed  
✓ MySQL Server installed and running  
✓ MySQL database created: `agriculture_management`

---

## 1️⃣ Download MySQL JDBC Driver

Your application needs the **MySQL Connector/J** driver to connect to the database.

### Option A: Download Directly (Recommended)
1. Visit: **https://dev.mysql.com/downloads/connector/j/**
2. Click **"Download"** on the latest version (8.0.x or higher)
3. Select **"Platform Independent"** 
4. Extract the ZIP file
5. Find the file named **`mysql-connector-java-X.X.X.jar`**

### Option B: Quick Download Link
- Direct download: https://dev.mysql.com/downloads/file/?id=527579
- (Or search for "mysql-connector-java" if link changes)

---

## 2️⃣ Place the JAR File

1. Copy the **`mysql-connector-java-X.X.X.jar`** file
2. Paste it into the **`lib`** folder in your project:
   ```
   c:\Users\vinay\OneDrive\Documents\DBMS\AgricultureManagement\lib\
   ```
3. Your project structure should look like:
   ```
   AgricultureManagement/
   ├── src/
   ├── lib/
   │   └── mysql-connector-java-X.X.X.jar  ← Put it here!
   ├── compile.bat
   ├── run.bat
   ├── compile_and_run.bat
   └── SETUP_INSTRUCTIONS.md
   ```

---

## 3️⃣ Setup MySQL Database

Open MySQL Command Prompt or MySQL Workbench and run:

```sql
CREATE DATABASE IF NOT EXISTS agriculture_management;
USE agriculture_management;

-- Tables will be created by your application on first run
```

**Database Connection Details:**
- Host: `localhost`
- Port: `3306`
- Username: `root`
- Password: `root`
- Database: `agriculture_management`

⚠️ **Note:** If your MySQL credentials are different, edit:
```
src\com\agriculture\database\DatabaseConnection.java
```
And change lines 8-10:
```java
private static final String DB_USER = "your_username";
private static final String DB_PASSWORD = "your_password";
```

---

## 4️⃣ Compile and Run

Now you have two options:

### Option A: Using Batch Files (EASIEST - Windows Only)
From your project root folder, double-click:
- **`compile_and_run.bat`** - Compiles and runs in one click
- **`compile.bat`** - Only compiles
- **`run.bat`** - Only runs

### Option B: Using Command Line
Open Command Prompt in your project root and run:

```batch
REM Compile:
cd src
javac -cp ".;..\lib\*" com\agriculture\*.java com\agriculture\database\*.java com\agriculture\models\*.java com\agriculture\dao\*.java com\agriculture\ui\*.java

REM Run:
java -cp ".;..\lib\*" com.agriculture.Main
```

---

## 5️⃣ Troubleshooting

### ❌ "MySQL JDBC Driver not found"
- Check if `mysql-connector-java-X.X.X.jar` is in the `lib` folder
- Verify the filename is correct (with `.jar` extension)
- Try re-downloading the driver

### ❌ "Cannot connect to database"
- Verify MySQL Server is running
- Check database name is `agriculture_management`
- Verify username/password in `DatabaseConnection.java`
- Make sure port 3306 is not blocked by firewall

### ❌ "Package not found" errors
- Delete all `.class` files and recompile
- Use the correct command from **Option A** or **Option B** above

---

## ✅ Success!

Once everything is set up correctly, you should see:
```
╔════════════════════════════════════════════════════╗
║   AGRICULTURE MANAGEMENT SYSTEM                   ║
║   Direct Connection Between Farmers & Customers   ║
╚════════════════════════════════════════════════════╝

[Connecting to Database...]
✓ Connection test: SUCCESS
✓ System Ready!
```

---

**Happy coding! 🎉**
