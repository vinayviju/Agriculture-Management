package com.agriculture.dao;

import com.agriculture.models.Farmer;
import com.agriculture.database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FarmerDAO {
    private Connection connection;

    public FarmerDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    // Create new farmer
    public boolean addFarmer(Farmer farmer) {
        String sql = "INSERT INTO FARMER (name, email, phone, address, city, state) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, farmer.getName());
            pstmt.setString(2, farmer.getEmail());
            pstmt.setString(3, farmer.getPhone());
            pstmt.setString(4, farmer.getAddress());
            pstmt.setString(5, farmer.getCity());
            pstmt.setString(6, farmer.getState());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error adding farmer: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Read farmer by ID
    public Farmer getFarmerById(int farmerId) {
        String sql = "SELECT * FROM FARMER WHERE farmer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, farmerId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractFarmerFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching farmer: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Read all farmers
    public List<Farmer> getAllFarmers() {
        List<Farmer> farmers = new ArrayList<>();
        String sql = "SELECT * FROM FARMER ORDER BY registration_date DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                farmers.add(extractFarmerFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching farmers: " + e.getMessage());
            e.printStackTrace();
        }
        return farmers;
    }

    // Update farmer
    public boolean updateFarmer(Farmer farmer) {
        String sql = "UPDATE FARMER SET name = ?, email = ?, phone = ?, address = ?, city = ?, state = ? WHERE farmer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, farmer.getName());
            pstmt.setString(2, farmer.getEmail());
            pstmt.setString(3, farmer.getPhone());
            pstmt.setString(4, farmer.getAddress());
            pstmt.setString(5, farmer.getCity());
            pstmt.setString(6, farmer.getState());
            pstmt.setInt(7, farmer.getFarmerId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating farmer: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Delete farmer
    public boolean deleteFarmer(int farmerId) {
        String sql = "DELETE FROM FARMER WHERE farmer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, farmerId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting farmer: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Search farmer by email
    public Farmer getFarmerByEmail(String email) {
        String sql = "SELECT * FROM FARMER WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractFarmerFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching farmer by email: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Search farmers by city
    public List<Farmer> getFarmersByCity(String city) {
        List<Farmer> farmers = new ArrayList<>();
        String sql = "SELECT * FROM FARMER WHERE city = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, city);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                farmers.add(extractFarmerFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching farmers by city: " + e.getMessage());
            e.printStackTrace();
        }
        return farmers;
    }

    // Helper method to extract farmer data from ResultSet
    private Farmer extractFarmerFromResultSet(ResultSet rs) throws SQLException {
        return new Farmer(
                rs.getInt("farmer_id"),
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
