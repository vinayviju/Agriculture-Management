package com.agriculture.dao;

import com.agriculture.models.Sales;
import com.agriculture.database.DatabaseConnection;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SalesDAO {
    private Connection connection;

    public SalesDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    // Create new sale
    public boolean addSale(Sales sale) {
        String sql = "INSERT INTO SALES (crop_id, customer_id, farmer_id, quantity_sold, total_price, delivery_status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, sale.getCropId());
            pstmt.setInt(2, sale.getCustomerId());
            pstmt.setInt(3, sale.getFarmerId());
            pstmt.setBigDecimal(4, sale.getQuantitySold());
            pstmt.setBigDecimal(5, sale.getTotalPrice());
            pstmt.setString(6, sale.getDeliveryStatus());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error adding sale: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Read sale by ID
    public Sales getSaleById(int saleId) {
        String sql = "SELECT * FROM SALES WHERE sale_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, saleId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractSalesFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching sale: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Read all sales
    public List<Sales> getAllSales() {
        List<Sales> sales = new ArrayList<>();
        String sql = "SELECT * FROM SALES ORDER BY sale_date DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                sales.add(extractSalesFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching sales: " + e.getMessage());
            e.printStackTrace();
        }
        return sales;
    }

    // Read sales by farmer ID
    public List<Sales> getSalesByFarmerId(int farmerId) {
        List<Sales> sales = new ArrayList<>();
        String sql = "SELECT * FROM SALES WHERE farmer_id = ? ORDER BY sale_date DESC";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, farmerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                sales.add(extractSalesFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching sales by farmer: " + e.getMessage());
            e.printStackTrace();
        }
        return sales;
    }

    // Read sales by customer ID
    public List<Sales> getSalesByCustomerId(int customerId) {
        List<Sales> sales = new ArrayList<>();
        String sql = "SELECT * FROM SALES WHERE customer_id = ? ORDER BY sale_date DESC";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                sales.add(extractSalesFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching sales by customer: " + e.getMessage());
            e.printStackTrace();
        }
        return sales;
    }

    // Update sale delivery status
    public boolean updateDeliveryStatus(int saleId, String status) {
        String sql = "UPDATE SALES SET delivery_status = ? WHERE sale_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, saleId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating delivery status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Delete sale
    public boolean deleteSale(int saleId) {
        String sql = "DELETE FROM SALES WHERE sale_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, saleId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting sale: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Get total sales amount by farmer
    public BigDecimal getTotalSalesByFarmer(int farmerId) {
        String sql = "SELECT SUM(total_price) as total FROM SALES WHERE farmer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, farmerId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getBigDecimal("total");
            }
        } catch (SQLException e) {
            System.out.println("Error calculating total sales: " + e.getMessage());
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    // Get sales by delivery status
    public List<Sales> getSalesByStatus(String status) {
        List<Sales> sales = new ArrayList<>();
        String sql = "SELECT * FROM SALES WHERE delivery_status = ? ORDER BY sale_date DESC";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                sales.add(extractSalesFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching sales by status: " + e.getMessage());
            e.printStackTrace();
        }
        return sales;
    }

    // Helper method to extract sales data from ResultSet
    private Sales extractSalesFromResultSet(ResultSet rs) throws SQLException {
        return new Sales(
                rs.getInt("sale_id"),
                rs.getInt("crop_id"),
                rs.getInt("customer_id"),
                rs.getInt("farmer_id"),
                rs.getBigDecimal("quantity_sold"),
                rs.getBigDecimal("total_price"),
                rs.getTimestamp("sale_date").toLocalDateTime(),
                rs.getString("delivery_status")
        );
    }
}
