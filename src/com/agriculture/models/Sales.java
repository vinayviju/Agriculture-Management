package com.agriculture.models;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class Sales {
    private int saleId;
    private int cropId;
    private int customerId;
    private int farmerId;
    private BigDecimal quantitySold;
    private BigDecimal totalPrice;
    private LocalDateTime saleDate;
    private String deliveryStatus;

    // Constructors
    public Sales() {
    }

    public Sales(int cropId, int customerId, int farmerId, BigDecimal quantitySold, BigDecimal totalPrice, String deliveryStatus) {
        this.cropId = cropId;
        this.customerId = customerId;
        this.farmerId = farmerId;
        this.quantitySold = quantitySold;
        this.totalPrice = totalPrice;
        this.deliveryStatus = deliveryStatus;
    }

    public Sales(int saleId, int cropId, int customerId, int farmerId, BigDecimal quantitySold, BigDecimal totalPrice, LocalDateTime saleDate, String deliveryStatus) {
        this.saleId = saleId;
        this.cropId = cropId;
        this.customerId = customerId;
        this.farmerId = farmerId;
        this.quantitySold = quantitySold;
        this.totalPrice = totalPrice;
        this.saleDate = saleDate;
        this.deliveryStatus = deliveryStatus;
    }

    // Getters and Setters
    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(int farmerId) {
        this.farmerId = farmerId;
    }

    public BigDecimal getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(BigDecimal quantitySold) {
        this.quantitySold = quantitySold;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    @Override
    public String toString() {
        return "Sales{" +
                "saleId=" + saleId +
                ", cropId=" + cropId +
                ", customerId=" + customerId +
                ", farmerId=" + farmerId +
                ", quantitySold=" + quantitySold +
                ", totalPrice=" + totalPrice +
                ", saleDate=" + saleDate +
                ", deliveryStatus='" + deliveryStatus + '\'' +
                '}';
    }
}
