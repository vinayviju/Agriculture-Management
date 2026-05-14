package com.agriculture;

import com.agriculture.database.DatabaseConnection;
import com.agriculture.ui.*;
import java.util.Scanner;

/**
 * Main Application Class for Agriculture Management System
 * This system connects farmers directly with customers for selling agricultural products
 */
public class Main {
    private static Scanner scanner;
    private static FarmerUI farmerUI;
    private static CropUI cropUI;
    private static CustomerUI customerUI;
    private static SalesUI salesUI;

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║   AGRICULTURE MANAGEMENT SYSTEM                   ║");
        System.out.println("║   Direct Connection Between Farmers & Customers   ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        
        // Initialize Scanner
        scanner = new Scanner(System.in);

        // Test database connection
        System.out.println("\n[Connecting to Database...]");
        if (!DatabaseConnection.testConnection()) {
            System.out.println("\n✗ Failed to connect to database!");
            System.out.println("Please ensure MySQL is running and agriculture_management database is created.");
            scanner.close();
            return;
        }

        // Initialize UI components
        farmerUI = new FarmerUI(scanner);
        cropUI = new CropUI(scanner);
        customerUI = new CustomerUI(scanner);
        salesUI = new SalesUI(scanner);

        System.out.println("✓ System Ready!\n");

        // Display main menu
        displayMainMenu();

        // Close resources
        scanner.close();
        DatabaseConnection.closeConnection();
        System.out.println("\nThank you for using Agriculture Management System!");
    }

    // Main Menu
    private static void displayMainMenu() {
        int choice = -1;
        do {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("           MAIN MENU - Agriculture Management");
            System.out.println("=".repeat(50));
            System.out.println("1. Farmer Management");
            System.out.println("2. Crop Management");
            System.out.println("3. Customer Management");
            System.out.println("4. Sales Management");
            System.out.println("0. Exit Application");
            System.out.print("\nEnter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        farmerUI.displayFarmerMenu();
                        break;
                    case 2:
                        cropUI.displayCropMenu();
                        break;
                    case 3:
                        customerUI.displayCustomerMenu();
                        break;
                    case 4:
                        salesUI.displaySalesMenu();
                        break;
                    case 0:
                        System.out.println("\n[Exiting Application...]");
                        return;
                    default:
                        System.out.println("✗ Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("✗ Invalid input! Please enter a number.");
                scanner.nextLine();
            }
        } while (choice != 0);
    }
}
