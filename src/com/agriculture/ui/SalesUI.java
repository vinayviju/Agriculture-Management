package com.agriculture.ui;

import com.agriculture.models.Sales;
import com.agriculture.dao.SalesDAO;
import com.agriculture.dao.CropDAO;
import com.agriculture.dao.FarmerDAO;
import com.agriculture.dao.CustomerDAO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class SalesUI {
    private SalesDAO salesDAO;
    private CropDAO cropDAO;
    private FarmerDAO farmerDAO;
    private CustomerDAO customerDAO;
    private Scanner scanner;

    public SalesUI(Scanner scanner) {
        this.salesDAO = new SalesDAO();
        this.cropDAO = new CropDAO();
        this.farmerDAO = new FarmerDAO();
        this.customerDAO = new CustomerDAO();
        this.scanner = scanner;
    }

    // Display sales menu
    public void displaySalesMenu() {
        int choice;
        do {
            System.out.println("\n========== SALES MANAGEMENT ==========");
            System.out.println("1. Record New Sale");
            System.out.println("2. View All Sales");
            System.out.println("3. View Sale Details");
            System.out.println("4. Update Delivery Status");
            System.out.println("5. View Sales by Farmer");
            System.out.println("6. View Sales by Customer");
            System.out.println("7. View Sales by Status");
            System.out.println("8. Get Farmer Total Sales");
            System.out.println("9. Delete Sale Record");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    recordSale();
                    break;
                case 2:
                    viewAllSales();
                    break;
                case 3:
                    viewSaleDetails();
                    break;
                case 4:
                    updateDeliveryStatus();
                    break;
                case 5:
                    viewSalesByFarmer();
                    break;
                case 6:
                    viewSalesByCustomer();
                    break;
                case 7:
                    viewSalesByStatus();
                    break;
                case 8:
                    getFarmerTotalSales();
                    break;
                case 9:
                    deleteSale();
                    break;
                case 0:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 0);
    }

    private void recordSale() {
        System.out.println("\n--- Record New Sale ---");
        System.out.print("Enter Crop ID: ");
        int cropId = scanner.nextInt();
        System.out.print("Enter Customer ID: ");
        int customerId = scanner.nextInt();
        System.out.print("Enter Farmer ID: ");
        int farmerId = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline after farmerId

        // Validate that all IDs exist
        if (cropDAO.getCropById(cropId) == null) {
            System.out.println("✗ Error: Crop ID " + cropId + " does not exist!");
            return;
        }
        
        if (customerDAO.getCustomerById(customerId) == null) {
            System.out.println("✗ Error: Customer ID " + customerId + " does not exist!");
            return;
        }
        
        if (farmerDAO.getFarmerById(farmerId) == null) {
            System.out.println("✗ Error: Farmer ID " + farmerId + " does not exist!");
            return;
        }

        BigDecimal quantity = promptBigDecimal("Enter Quantity Sold: ");
        BigDecimal totalPrice = promptBigDecimal("Enter Total Price: ");
        System.out.print("Enter Delivery Status (Pending/Delivered/Cancelled): ");
        String status = scanner.nextLine();

        Sales sale = new Sales(cropId, customerId, farmerId, quantity, totalPrice, status);
        if (salesDAO.addSale(sale)) {
            // Update crop quantity
            cropDAO.updateQuantity(cropId,
                cropDAO.getCropById(cropId).getQuantityAvailable().subtract(quantity));
            System.out.println("✓ Sale recorded successfully!");
        } else {
            System.out.println("✗ Failed to record sale.");
        }
    }

    private BigDecimal promptBigDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = scanner.nextLine().trim();
            try {
                return new BigDecimal(text);
            } catch (NumberFormatException e) {
                System.out.println("✗ Invalid input! Please enter a valid numeric value.");
            }
        }
    }

    private void viewAllSales() {
        System.out.println("\n--- All Sales ---");
        List<Sales> sales = salesDAO.getAllSales();
        if (sales.isEmpty()) {
            System.out.println("No sales found.");
        } else {
            System.out.println(String.format("%-5s %-5s %-5s %-5s %-8s %-12s %-12s", 
                "SID", "Crop", "Customer", "Farmer", "Qty", "Price", "Status"));
            System.out.println("-------------------------------------------------------------");
            for (Sales sale : sales) {
                System.out.println(String.format("%-5d %-5d %-5d %-5d %-8s %-12s %-12s",
                    sale.getSaleId(), sale.getCropId(), sale.getCustomerId(), sale.getFarmerId(),
                    sale.getQuantitySold(), sale.getTotalPrice(), sale.getDeliveryStatus()));
            }
        }
    }

    private void viewSaleDetails() {
        System.out.print("\nEnter Sale ID: ");
        int saleId = scanner.nextInt();
        scanner.nextLine();

        Sales sale = salesDAO.getSaleById(saleId);
        if (sale != null) {
            System.out.println("\n--- Sale Details ---");
            System.out.println("Sale ID: " + sale.getSaleId());
            System.out.println("Crop ID: " + sale.getCropId());
            System.out.println("Customer ID: " + sale.getCustomerId());
            System.out.println("Farmer ID: " + sale.getFarmerId());
            System.out.println("Quantity Sold: " + sale.getQuantitySold());
            System.out.println("Total Price: ₹" + sale.getTotalPrice());
            System.out.println("Sale Date: " + sale.getSaleDate());
            System.out.println("Delivery Status: " + sale.getDeliveryStatus());
        } else {
            System.out.println("✗ Sale not found.");
        }
    }

    private void updateDeliveryStatus() {
        System.out.print("\nEnter Sale ID: ");
        int saleId = scanner.nextInt();
        scanner.nextLine();

        Sales sale = salesDAO.getSaleById(saleId);
        if (sale != null) {
            System.out.print("Enter New Status (Pending/Delivered/Cancelled): ");
            String newStatus = scanner.nextLine();

            if (salesDAO.updateDeliveryStatus(saleId, newStatus)) {
                System.out.println("✓ Delivery status updated successfully!");
            } else {
                System.out.println("✗ Failed to update delivery status.");
            }
        } else {
            System.out.println("✗ Sale not found.");
        }
    }

    private void viewSalesByFarmer() {
        System.out.print("\nEnter Farmer ID: ");
        int farmerId = scanner.nextInt();
        scanner.nextLine();

        List<Sales> sales = salesDAO.getSalesByFarmerId(farmerId);
        if (sales.isEmpty()) {
            System.out.println("No sales found for this farmer.");
        } else {
            System.out.println("\n--- Sales by Farmer ID: " + farmerId + " ---");
            System.out.println(String.format("%-5s %-5s %-5s %-8s %-12s %-12s",
                "SID", "Crop", "Customer", "Qty", "Price", "Status"));
            System.out.println("--------------------------------------------------");
            for (Sales sale : sales) {
                System.out.println(String.format("%-5d %-5d %-5d %-8s %-12s %-12s",
                    sale.getSaleId(), sale.getCropId(), sale.getCustomerId(),
                    sale.getQuantitySold(), sale.getTotalPrice(), sale.getDeliveryStatus()));
            }
        }
    }

    private void viewSalesByCustomer() {
        System.out.print("\nEnter Customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        List<Sales> sales = salesDAO.getSalesByCustomerId(customerId);
        if (sales.isEmpty()) {
            System.out.println("No sales found for this customer.");
        } else {
            System.out.println("\n--- Sales by Customer ID: " + customerId + " ---");
            System.out.println(String.format("%-5s %-5s %-5s %-8s %-12s %-12s",
                "SID", "Crop", "Farmer", "Qty", "Price", "Status"));
            System.out.println("--------------------------------------------------");
            for (Sales sale : sales) {
                System.out.println(String.format("%-5d %-5d %-5d %-8s %-12s %-12s",
                    sale.getSaleId(), sale.getCropId(), sale.getFarmerId(),
                    sale.getQuantitySold(), sale.getTotalPrice(), sale.getDeliveryStatus()));
            }
        }
    }

    private void viewSalesByStatus() {
        System.out.print("\nEnter Status (Pending/Delivered/Cancelled): ");
        String status = scanner.nextLine();

        List<Sales> sales = salesDAO.getSalesByStatus(status);
        if (sales.isEmpty()) {
            System.out.println("No sales found with status: " + status);
        } else {
            System.out.println("\n--- Sales with Status: " + status + " ---");
            System.out.println(String.format("%-5s %-5s %-5s %-5s %-8s %-12s",
                "SID", "Crop", "Customer", "Farmer", "Qty", "Price"));
            System.out.println("-----------------------------------------------------");
            for (Sales sale : sales) {
                System.out.println(String.format("%-5d %-5d %-5d %-5d %-8s %-12s",
                    sale.getSaleId(), sale.getCropId(), sale.getCustomerId(), sale.getFarmerId(),
                    sale.getQuantitySold(), sale.getTotalPrice()));
            }
        }
    }

    private void getFarmerTotalSales() {
        System.out.print("\nEnter Farmer ID: ");
        int farmerId = scanner.nextInt();
        scanner.nextLine();

        BigDecimal totalSales = salesDAO.getTotalSalesByFarmer(farmerId);
        System.out.println("\nTotal Sales Amount for Farmer ID " + farmerId + ": ₹" + totalSales);
    }

    private void deleteSale() {
        System.out.print("\nEnter Sale ID to Delete: ");
        int saleId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Are you sure? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            if (salesDAO.deleteSale(saleId)) {
                System.out.println("✓ Sale record deleted successfully!");
            } else {
                System.out.println("✗ Failed to delete sale record.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
}
