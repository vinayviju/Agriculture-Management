package com.agriculture.ui;

import com.agriculture.models.Customer;
import com.agriculture.dao.CustomerDAO;

import java.util.List;
import java.util.Scanner;

public class CustomerUI {
    private CustomerDAO customerDAO;
    private Scanner scanner;

    public CustomerUI(Scanner scanner) {
        this.customerDAO = new CustomerDAO();
        this.scanner = scanner;
    }

    // Display customer menu
    public void displayCustomerMenu() {
        int choice;
        do {
            System.out.println("\n========== CUSTOMER MANAGEMENT ==========");
            System.out.println("1. Add New Customer");
            System.out.println("2. View All Customers");
            System.out.println("3. View Customer Details");
            System.out.println("4. Update Customer Information");
            System.out.println("5. Delete Customer");
            System.out.println("6. Search Customers by City");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    viewAllCustomers();
                    break;
                case 3:
                    viewCustomerDetails();
                    break;
                case 4:
                    updateCustomer();
                    break;
                case 5:
                    deleteCustomer();
                    break;
                case 6:
                    searchCustomersByCity();
                    break;
                case 0:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 0);
    }

    private void addCustomer() {
        System.out.println("\n--- Add New Customer ---");
        System.out.print("Enter Customer Name: ");
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

        Customer customer = new Customer(name, email, phone, address, city, state);
        if (customerDAO.addCustomer(customer)) {
            System.out.println("✓ Customer added successfully!");
        } else {
            System.out.println("✗ Failed to add customer.");
        }
    }

    private void viewAllCustomers() {
        System.out.println("\n--- All Customers ---");
        List<Customer> customers = customerDAO.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println(String.format("%-5s %-20s %-25s %-15s", "ID", "Name", "Email", "Phone"));
            System.out.println("---------------------------------------------------------------");
            for (Customer customer : customers) {
                System.out.println(String.format("%-5d %-20s %-25s %-15s",
                    customer.getCustomerId(), customer.getName(), customer.getEmail(), customer.getPhone()));
            }
        }
    }

    private void viewCustomerDetails() {
        System.out.print("\nEnter Customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        Customer customer = customerDAO.getCustomerById(customerId);
        if (customer != null) {
            System.out.println("\n--- Customer Details ---");
            System.out.println("ID: " + customer.getCustomerId());
            System.out.println("Name: " + customer.getName());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Phone: " + customer.getPhone());
            System.out.println("Address: " + customer.getAddress());
            System.out.println("City: " + customer.getCity());
            System.out.println("State: " + customer.getState());
            System.out.println("Registration Date: " + customer.getRegistrationDate());
        } else {
            System.out.println("✗ Customer not found.");
        }
    }

    private void updateCustomer() {
        System.out.print("\nEnter Customer ID to Update: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        Customer customer = customerDAO.getCustomerById(customerId);
        if (customer != null) {
            System.out.println("--- Update Customer ---");
            System.out.print("Enter New Name (current: " + customer.getName() + "): ");
            String name = scanner.nextLine();
            System.out.print("Enter New Email (current: " + customer.getEmail() + "): ");
            String email = scanner.nextLine();
            System.out.print("Enter New Phone (current: " + customer.getPhone() + "): ");
            String phone = scanner.nextLine();
            System.out.print("Enter New Address (current: " + customer.getAddress() + "): ");
            String address = scanner.nextLine();
            System.out.print("Enter New City (current: " + customer.getCity() + "): ");
            String city = scanner.nextLine();
            System.out.print("Enter New State (current: " + customer.getState() + "): ");
            String state = scanner.nextLine();

            customer.setName(name.isEmpty() ? customer.getName() : name);
            customer.setEmail(email.isEmpty() ? customer.getEmail() : email);
            String updatedPhone = phone.isEmpty() ? customer.getPhone() : promptValidPhone("Enter New Phone (current: " + customer.getPhone() + "): ");
            customer.setPhone(updatedPhone);
            customer.setAddress(address.isEmpty() ? customer.getAddress() : address);
            customer.setCity(city.isEmpty() ? customer.getCity() : city);
            customer.setState(state.isEmpty() ? customer.getState() : state);

            if (customerDAO.updateCustomer(customer)) {
                System.out.println("✓ Customer updated successfully!");
            } else {
                System.out.println("✗ Failed to update customer.");
            }
        } else {
            System.out.println("✗ Customer not found.");
        }
    }

    private void deleteCustomer() {
        System.out.print("\nEnter Customer ID to Delete: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Are you sure? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            if (customerDAO.deleteCustomer(customerId)) {
                System.out.println("✓ Customer deleted successfully!");
            } else {
                System.out.println("✗ Failed to delete customer.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private void searchCustomersByCity() {
        System.out.print("\nEnter City Name: ");
        String city = scanner.nextLine();

        List<Customer> customers = customerDAO.getCustomersByCity(city);
        if (customers.isEmpty()) {
            System.out.println("No customers found in " + city);
        } else {
            System.out.println("\n--- Customers in " + city + " ---");
            System.out.println(String.format("%-5s %-20s %-25s %-15s", "ID", "Name", "Email", "Phone"));
            System.out.println("---------------------------------------------------------------");
            for (Customer customer : customers) {
                System.out.println(String.format("%-5d %-20s %-25s %-15s",
                    customer.getCustomerId(), customer.getName(), customer.getEmail(), customer.getPhone()));
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
