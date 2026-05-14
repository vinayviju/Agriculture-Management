package com.agriculture.dao;

import com.agriculture.models.Crop;
import com.agriculture.database.DatabaseConnection;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CropDAO {
    private Connection connection;

    public CropDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    // Create new crop
    public boolean addCrop(Crop crop) {
        String sql = "INSERT INTO CROP (farmer_id, crop_name, crop_type, quantity_available, price_per_unit, unit, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, crop.getFarmerId());
            pstmt.setString(2, crop.getCropName());
            pstmt.setString(3, crop.getCropType());
            pstmt.setBigDecimal(4, crop.getQuantityAvailable());
            pstmt.setBigDecimal(5, crop.getPricePerUnit());
            pstmt.setString(6, crop.getUnit());
            pstmt.setString(7, crop.getDescription());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error adding crop: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Read crop by ID
    public Crop getCropById(int cropId) {
        String sql = "SELECT * FROM CROP WHERE crop_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, cropId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractCropFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching crop: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Read all crops
    public List<Crop> getAllCrops() {
        List<Crop> crops = new ArrayList<>();
        String sql = "SELECT * FROM CROP ORDER BY registration_date DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                crops.add(extractCropFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching crops: " + e.getMessage());
            e.printStackTrace();
        }
        return crops;
    }

    // Read crops by farmer ID
    public List<Crop> getCropsByFarmerId(int farmerId) {
        List<Crop> crops = new ArrayList<>();
        String sql = "SELECT * FROM CROP WHERE farmer_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, farmerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                crops.add(extractCropFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching crops by farmer: " + e.getMessage());
            e.printStackTrace();
        }
        return crops;
    }

    // Update crop
    public boolean updateCrop(Crop crop) {
        String sql = "UPDATE CROP SET crop_name = ?, crop_type = ?, quantity_available = ?, price_per_unit = ?, unit = ?, description = ? WHERE crop_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, crop.getCropName());
            pstmt.setString(2, crop.getCropType());
            pstmt.setBigDecimal(3, crop.getQuantityAvailable());
            pstmt.setBigDecimal(4, crop.getPricePerUnit());
            pstmt.setString(5, crop.getUnit());
            pstmt.setString(6, crop.getDescription());
            pstmt.setInt(7, crop.getCropId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating crop: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Delete crop
    public boolean deleteCrop(int cropId) {
        String sql = "DELETE FROM CROP WHERE crop_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, cropId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting crop: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Search crops by type
    public List<Crop> getCropsByType(String cropType) {
        List<Crop> crops = new ArrayList<>();
        String sql = "SELECT * FROM CROP WHERE crop_type = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cropType);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                crops.add(extractCropFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching crops by type: " + e.getMessage());
            e.printStackTrace();
        }
        return crops;
    }

    // Update quantity available
    public boolean updateQuantity(int cropId, BigDecimal newQuantity) {
        String sql = "UPDATE CROP SET quantity_available = ? WHERE crop_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBigDecimal(1, newQuantity);
            pstmt.setInt(2, cropId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating quantity: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to extract crop data from ResultSet
    private Crop extractCropFromResultSet(ResultSet rs) throws SQLException {
        return new Crop(
                rs.getInt("crop_id"),
                rs.getInt("farmer_id"),
                rs.getString("crop_name"),
                rs.getString("crop_type"),
                rs.getBigDecimal("quantity_available"),
                rs.getBigDecimal("price_per_unit"),
                rs.getString("unit"),
                rs.getString("description"),
                rs.getTimestamp("registration_date").toLocalDateTime()
        );
    }
}
