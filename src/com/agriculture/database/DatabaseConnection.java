package com.agriculture.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/agriculture_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private static Connection connection = null;

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        }
    }

    // Method to establish database connection
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("Database connection established successfully!");
                createTables();
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to database!");
            e.printStackTrace();
        }
        return connection;
    }

    // Method to create required tables if they do not exist
    private static void createTables() {
        try (Statement stmt = connection.createStatement()) {
            String createFarmerTable = "CREATE TABLE IF NOT EXISTS FARMER (" +
                    "farmer_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) UNIQUE, " +
                    "phone VARCHAR(50), " +
                    "address VARCHAR(255), " +
                    "city VARCHAR(100), " +
                    "state VARCHAR(100), " +
                    "registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ") ENGINE=InnoDB";

            String createCustomerTable = "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
                    "customer_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) UNIQUE, " +
                    "phone VARCHAR(50), " +
                    "address VARCHAR(255), " +
                    "city VARCHAR(100), " +
                    "state VARCHAR(100), " +
                    "registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ") ENGINE=InnoDB";

            String createCropTable = "CREATE TABLE IF NOT EXISTS CROP (" +
                    "crop_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "farmer_id INT NOT NULL, " +
                    "crop_name VARCHAR(100) NOT NULL, " +
                    "crop_type VARCHAR(100), " +
                    "quantity_available DECIMAL(10,2), " +
                    "price_per_unit DECIMAL(10,2), " +
                    "unit VARCHAR(50), " +
                    "description VARCHAR(255), " +
                    "registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (farmer_id) REFERENCES FARMER(farmer_id) ON DELETE CASCADE" +
                    ") ENGINE=InnoDB";

            String createSalesTable = "CREATE TABLE IF NOT EXISTS SALES (" +
                    "sale_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "crop_id INT NOT NULL, " +
                    "customer_id INT NOT NULL, " +
                    "farmer_id INT NOT NULL, " +
                    "quantity_sold DECIMAL(10,2), " +
                    "total_price DECIMAL(10,2), " +
                    "sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "delivery_status VARCHAR(50), " +
                    "FOREIGN KEY (crop_id) REFERENCES CROP(crop_id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (customer_id) REFERENCES CUSTOMER(customer_id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (farmer_id) REFERENCES FARMER(farmer_id) ON DELETE CASCADE" +
                    ") ENGINE=InnoDB";

            stmt.executeUpdate(createFarmerTable);
            stmt.executeUpdate(createCustomerTable);
            stmt.executeUpdate(createCropTable);
            stmt.executeUpdate(createSalesTable);
        } catch (SQLException e) {
            System.out.println("Error creating database tables!");
            e.printStackTrace();
        }
    }

    // Method to close database connection
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed!");
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection!");
            e.printStackTrace();
        }
    }

    // Method to test connection
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("Connection test: SUCCESS");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
