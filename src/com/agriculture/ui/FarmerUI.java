package com.agriculture.ui;

import com.agriculture.models.Farmer;
import com.agriculture.dao.FarmerDAO;

import java.util.List;
import java.util.Scanner;

public class FarmerUI {
    private FarmerDAO farmerDAO;
    private Scanner scanner;

    public FarmerUI(Scanner scanner) {
        this.farmerDAO = new FarmerDAO();
        this.scanner = scanner;
    }

    // Display farmer menu
    public void displayFarmerMenu() {
        int choice;
        do {
            System.out.println("\n========== FARMER MANAGEMENT ==========");
            System.out.println("1. Add New Farmer");
            System.out.println("2. View All Farmers");
            System.out.println("3. View Farmer Details");
            System.out.println("4. Update Farmer Information");
            System.out.println("5. Delete Farmer");
            System.out.println("6. Search Farmers by City");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addFarmer();
                    break;
                case 2:
                    viewAllFarmers();
                    break;
                case 3:
                    viewFarmerDetails();
                    break;
                case 4:
                    updateFarmer();
                    break;
                case 5:
                    deleteFarmer();
                    break;
                case 6:
                    searchFarmersByCity();
                    break;
                case 0:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 0);
    }

    private void addFarmer() {
        System.out.println("\n--- Add New Farmer ---");
        System.out.print("Enter Farmer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        String phone = promptValidPhone("Enter Phone: ");
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter City: ");
        String city = scanner.nextLine();
        System.out.print("Enter State: ");
        String state = scanner.nextLine();

        Farmer farmer = new Farmer(name, email, phone, address, city, state);
        if (farmerDAO.addFarmer(farmer)) {
            System.out.println("✓ Farmer added successfully!");
        } else {
            System.out.println("✗ Failed to add farmer.");
        }
    }

    private void viewAllFarmers() {
        System.out.println("\n--- All Farmers ---");
        List<Farmer> farmers = farmerDAO.getAllFarmers();
        if (farmers.isEmpty()) {
            System.out.println("No farmers found.");
        } else {
            System.out.println(String.format("%-5s %-20s %-25s %-15s", "ID", "Name", "Email", "Phone"));
            System.out.println("---------------------------------------------------------------");
            for (Farmer farmer : farmers) {
                System.out.println(String.format("%-5d %-20s %-25s %-15s", 
                    farmer.getFarmerId(), farmer.getName(), farmer.getEmail(), farmer.getPhone()));
            }
        }
    }

    private void viewFarmerDetails() {
        System.out.print("\nEnter Farmer ID: ");
        int farmerId = scanner.nextInt();
        scanner.nextLine();

        Farmer farmer = farmerDAO.getFarmerById(farmerId);
        if (farmer != null) {
            System.out.println("\n--- Farmer Details ---");
            System.out.println("ID: " + farmer.getFarmerId());
            System.out.println("Name: " + farmer.getName());
            System.out.println("Email: " + farmer.getEmail());
            System.out.println("Phone: " + farmer.getPhone());
            System.out.println("Address: " + farmer.getAddress());
            System.out.println("City: " + farmer.getCity());
            System.out.println("State: " + farmer.getState());
            System.out.println("Registration Date: " + farmer.getRegistrationDate());
        } else {
            System.out.println("✗ Farmer not found.");
        }
    }

    private void updateFarmer() {
        System.out.print("\nEnter Farmer ID to Update: ");
        int farmerId = scanner.nextInt();
        scanner.nextLine();

        Farmer farmer = farmerDAO.getFarmerById(farmerId);
        if (farmer != null) {
            System.out.println("--- Update Farmer ---");
            System.out.print("Enter New Name (current: " + farmer.getName() + "): ");
            String name = scanner.nextLine();
            System.out.print("Enter New Email (current: " + farmer.getEmail() + "): ");
            String email = scanner.nextLine();
            System.out.print("Enter New Phone (current: " + farmer.getPhone() + "): ");
            String phone = scanner.nextLine();
            System.out.print("Enter New Address (current: " + farmer.getAddress() + "): ");
            String address = scanner.nextLine();
            System.out.print("Enter New City (current: " + farmer.getCity() + "): ");
            String city = scanner.nextLine();
            System.out.print("Enter New State (current: " + farmer.getState() + "): ");
            String state = scanner.nextLine();

            farmer.setName(name.isEmpty() ? farmer.getName() : name);
            farmer.setEmail(email.isEmpty() ? farmer.getEmail() : email);
            String updatedPhone = phone.isEmpty() ? farmer.getPhone() : promptValidPhone("Enter New Phone (current: " + farmer.getPhone() + "): ");
            farmer.setPhone(updatedPhone);
            farmer.setAddress(address.isEmpty() ? farmer.getAddress() : address);
            farmer.setCity(city.isEmpty() ? farmer.getCity() : city);
            farmer.setState(state.isEmpty() ? farmer.getState() : state);

            if (farmerDAO.updateFarmer(farmer)) {
                System.out.println("✓ Farmer updated successfully!");
            } else {
                System.out.println("✗ Failed to update farmer.");
            }
        } else {
            System.out.println("✗ Farmer not found.");
        }
    }

    private void deleteFarmer() {
        System.out.print("\nEnter Farmer ID to Delete: ");
        int farmerId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Are you sure? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            if (farmerDAO.deleteFarmer(farmerId)) {
                System.out.println("✓ Farmer deleted successfully!");
            } else {
                System.out.println("✗ Failed to delete farmer.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private void searchFarmersByCity() {
        System.out.print("\nEnter City Name: ");
        String city = scanner.nextLine();

        List<Farmer> farmers = farmerDAO.getFarmersByCity(city);
        if (farmers.isEmpty()) {
            System.out.println("No farmers found in " + city);
        } else {
            System.out.println("\n--- Farmers in " + city + " ---");
            System.out.println(String.format("%-5s %-20s %-25s %-15s", "ID", "Name", "Email", "Phone"));
            System.out.println("---------------------------------------------------------------");
            for (Farmer farmer : farmers) {
                System.out.println(String.format("%-5d %-20s %-25s %-15s", 
                    farmer.getFarmerId(), farmer.getName(), farmer.getEmail(), farmer.getPhone()));
            }
        }
    }

    private String promptValidPhone(String prompt) {
        while (true) {
            System.out.print(prompt);
            String phone = scanner.nextLine().trim();
            if (phone.matches("\\d{10}")) {
                return phone;
            }
            System.out.println("Phone number must be exactly 10 digits. Please enter a 10-digit number without spaces or symbols.");
        }
    }
}
