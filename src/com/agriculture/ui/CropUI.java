package com.agriculture.ui;

import com.agriculture.models.Crop;
import com.agriculture.dao.CropDAO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class CropUI {
    private CropDAO cropDAO;
    private Scanner scanner;

    public CropUI(Scanner scanner) {
        this.cropDAO = new CropDAO();
        this.scanner = scanner;
    }

    // Display crop menu
    public void displayCropMenu() {
        int choice;
        do {
            System.out.println("\n========== CROP MANAGEMENT ==========");
            System.out.println("1. Add New Crop");
            System.out.println("2. View All Crops");
            System.out.println("3. View Crop Details");
            System.out.println("4. Update Crop Information");
            System.out.println("5. Delete Crop");
            System.out.println("6. Search Crops by Type");
            System.out.println("7. View Crops by Farmer");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addCrop();
                    break;
                case 2:
                    viewAllCrops();
                    break;
                case 3:
                    viewCropDetails();
                    break;
                case 4:
                    updateCrop();
                    break;
                case 5:
                    deleteCrop();
                    break;
                case 6:
                    searchCropsByType();
                    break;
                case 7:
                    viewCropsByFarmer();
                    break;
                case 0:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 0);
    }

    private void addCrop() {
        System.out.println("\n--- Add New Crop ---");
        System.out.print("Enter Farmer ID: ");
        int farmerId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Crop Name: ");
        String cropName = scanner.nextLine();
        System.out.print("Enter Crop Type (Vegetable/Fruit/Grain): ");
        String cropType = scanner.nextLine();
        System.out.print("Enter Quantity Available (Kg/Liters): ");
        BigDecimal quantity = new BigDecimal(scanner.nextLine());
        System.out.print("Enter Price Per Unit: ");
        BigDecimal price = new BigDecimal(scanner.nextLine());
        System.out.print("Enter Unit (Kg/Liters/etc): ");
        String unit = scanner.nextLine();
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        Crop crop = new Crop(farmerId, cropName, cropType, quantity, price, unit, description);
        if (cropDAO.addCrop(crop)) {
            System.out.println("✓ Crop added successfully!");
        } else {
            System.out.println("✗ Failed to add crop.");
        }
    }

    private void viewAllCrops() {
        System.out.println("\n--- All Crops ---");
        List<Crop> crops = cropDAO.getAllCrops();
        if (crops.isEmpty()) {
            System.out.println("No crops found.");
        } else {
            System.out.println(String.format("%-5s %-5s %-15s %-12s %-8s %-10s", "ID", "Farmer", "Crop Name", "Type", "Qty", "Price"));
            System.out.println("---------------------------------------------------------------");
            for (Crop crop : crops) {
                System.out.println(String.format("%-5d %-5d %-15s %-12s %-8s %-10s",
                    crop.getCropId(), crop.getFarmerId(), crop.getCropName(), crop.getCropType(), 
                    crop.getQuantityAvailable(), crop.getPricePerUnit()));
            }
        }
    }

    private void viewCropDetails() {
        System.out.print("\nEnter Crop ID: ");
        int cropId = scanner.nextInt();
        scanner.nextLine();

        Crop crop = cropDAO.getCropById(cropId);
        if (crop != null) {
            System.out.println("\n--- Crop Details ---");
            System.out.println("Crop ID: " + crop.getCropId());
            System.out.println("Farmer ID: " + crop.getFarmerId());
            System.out.println("Crop Name: " + crop.getCropName());
            System.out.println("Type: " + crop.getCropType());
            System.out.println("Quantity Available: " + crop.getQuantityAvailable() + " " + crop.getUnit());
            System.out.println("Price Per Unit: ₹" + crop.getPricePerUnit());
            System.out.println("Description: " + crop.getDescription());
            System.out.println("Registration Date: " + crop.getRegistrationDate());
        } else {
            System.out.println("✗ Crop not found.");
        }
    }

    private void updateCrop() {
        System.out.print("\nEnter Crop ID to Update: ");
        int cropId = scanner.nextInt();
        scanner.nextLine();

        Crop crop = cropDAO.getCropById(cropId);
        if (crop != null) {
            System.out.println("--- Update Crop ---");
            System.out.print("Enter New Crop Name (current: " + crop.getCropName() + "): ");
            String cropName = scanner.nextLine();
            System.out.print("Enter New Quantity (current: " + crop.getQuantityAvailable() + "): ");
            String quantity = scanner.nextLine();
            System.out.print("Enter New Price (current: " + crop.getPricePerUnit() + "): ");
            String price = scanner.nextLine();

            crop.setCropName(cropName.isEmpty() ? crop.getCropName() : cropName);
            if (!quantity.isEmpty()) {
                crop.setQuantityAvailable(new BigDecimal(quantity));
            }
            if (!price.isEmpty()) {
                crop.setPricePerUnit(new BigDecimal(price));
            }

            if (cropDAO.updateCrop(crop)) {
                System.out.println("✓ Crop updated successfully!");
            } else {
                System.out.println("✗ Failed to update crop.");
            }
        } else {
            System.out.println("✗ Crop not found.");
        }
    }

    private void deleteCrop() {
        System.out.print("\nEnter Crop ID to Delete: ");
        int cropId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Are you sure? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            if (cropDAO.deleteCrop(cropId)) {
                System.out.println("✓ Crop deleted successfully!");
            } else {
                System.out.println("✗ Failed to delete crop.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private void searchCropsByType() {
        System.out.print("\nEnter Crop Type (Vegetable/Fruit/Grain): ");
        String cropType = scanner.nextLine();

        List<Crop> crops = cropDAO.getCropsByType(cropType);
        if (crops.isEmpty()) {
            System.out.println("No crops of type '" + cropType + "' found.");
        } else {
            System.out.println("\n--- Crops of Type: " + cropType + " ---");
            System.out.println(String.format("%-5s %-5s %-15s %-8s %-10s", "ID", "Farmer", "Crop Name", "Qty", "Price"));
            System.out.println("--------------------------------------------------");
            for (Crop crop : crops) {
                System.out.println(String.format("%-5d %-5d %-15s %-8s %-10s",
                    crop.getCropId(), crop.getFarmerId(), crop.getCropName(),
                    crop.getQuantityAvailable(), crop.getPricePerUnit()));
            }
        }
    }

    private void viewCropsByFarmer() {
        System.out.print("\nEnter Farmer ID: ");
        int farmerId = scanner.nextInt();
        scanner.nextLine();

        List<Crop> crops = cropDAO.getCropsByFarmerId(farmerId);
        if (crops.isEmpty()) {
            System.out.println("No crops found for this farmer.");
        } else {
            System.out.println("\n--- Crops for Farmer ID: " + farmerId + " ---");
            System.out.println(String.format("%-5s %-15s %-12s %-8s %-10s", "ID", "Crop Name", "Type", "Qty", "Price"));
            System.out.println("-----------------------------------------------------");
            for (Crop crop : crops) {
                System.out.println(String.format("%-5d %-15s %-12s %-8s %-10s",
                    crop.getCropId(), crop.getCropName(), crop.getCropType(),
                    crop.getQuantityAvailable(), crop.getPricePerUnit()));
            }
        }
    }
}
