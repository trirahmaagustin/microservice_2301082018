package com.teknologiinformasitka.restapi.order.model;

public class UpdateOrderRequest {
    private Long productId;
    private int quantity;
    private String orderStatus;
 
 
    // Getters & Setters
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
 }
 
