package com.agriculture.dao;

import com.agriculture.models.Customer;
import com.agriculture.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private Connection connection;

    public CustomerDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    // Create new customer
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO CUSTOMER (name, email, phone, address, city, state) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getEmail());
            pstmt.setString(3, customer.getPhone());
            pstmt.setString(4, customer.getAddress());
            pstmt.setString(5, customer.getCity());
            pstmt.setString(6, customer.getState());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error adding customer: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Read customer by ID
    public Customer getCustomerById(int customerId) {
        String sql = "SELECT * FROM CUSTOMER WHERE customer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractCustomerFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching customer: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Read all customers
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM CUSTOMER ORDER BY registration_date DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                customers.add(extractCustomerFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching customers: " + e.getMessage());
            e.printStackTrace();
        }
        return customers;
    }

    // Update customer
    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE CUSTOMER SET name = ?, email = ?, phone = ?, address = ?, city = ?, state = ? WHERE customer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getEmail());
            pstmt.setString(3, customer.getPhone());
            pstmt.setString(4, customer.getAddress());
            pstmt.setString(5, customer.getCity());
            pstmt.setString(6, customer.getState());
            pstmt.setInt(7, customer.getCustomerId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating customer: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Delete customer
    public boolean deleteCustomer(int customerId) {
        String sql = "DELETE FROM CUSTOMER WHERE customer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting customer: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Search customer by email
    public Customer getCustomerByEmail(String email) {
        String sql = "SELECT * FROM CUSTOMER WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractCustomerFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching customer by email: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Search customers by city
    public List<Customer> getCustomersByCity(String city) {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM CUSTOMER WHERE city = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, city);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                customers.add(extractCustomerFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching customers by city: " + e.getMessage());
            e.printStackTrace();
        }
        return customers;
    }

    // Helper method to extract customer data from ResultSet
    private Customer extractCustomerFromResultSet(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getInt("customer_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("address"),
                rs.getString("city"),
                rs.getString("state"),
                rs.getTimestamp("registration_date").toLocalDateTime()
        );
    }
}
