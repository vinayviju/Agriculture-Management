package com.agriculture.models;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class Crop {
    private int cropId;
    private int farmerId;
    private String cropName;
    private String cropType;
    private BigDecimal quantityAvailable;
    private BigDecimal pricePerUnit;
    private String unit;
    private String description;
    private LocalDateTime registrationDate;

    // Constructors
    public Crop() {
    }

    public Crop(int farmerId, String cropName, String cropType, BigDecimal quantityAvailable, BigDecimal pricePerUnit, String unit, String description) {
        this.farmerId = farmerId;
        this.cropName = cropName;
        this.cropType = cropType;
        this.quantityAvailable = quantityAvailable;
        this.pricePerUnit = pricePerUnit;
        this.unit = unit;
        this.description = description;
    }

    public Crop(int cropId, int farmerId, String cropName, String cropType, BigDecimal quantityAvailable, BigDecimal pricePerUnit, String unit, String description, LocalDateTime registrationDate) {
        this.cropId = cropId;
        this.farmerId = farmerId;
        this.cropName = cropName;
        this.cropType = cropType;
        this.quantityAvailable = quantityAvailable;
        this.pricePerUnit = pricePerUnit;
        this.unit = unit;
        this.description = description;
        this.registrationDate = registrationDate;
    }

    // Getters and Setters
    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }

    public int getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(int farmerId) {
        this.farmerId = farmerId;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public BigDecimal getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(BigDecimal quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "Crop{" +
                "cropId=" + cropId +
                ", farmerId=" + farmerId +
                ", cropName='" + cropName + '\'' +
                ", cropType='" + cropType + '\'' +
                ", quantityAvailable=" + quantityAvailable +
                ", pricePerUnit=" + pricePerUnit +
                ", unit='" + unit + '\'' +
                ", description='" + description + '\'' +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
