package com.dnd.easyorder.model;

public class PlaceOrderResponse {

    private Long orderId;

    private String status;

    private String message;

    public PlaceOrderResponse() {

    }
    public PlaceOrderResponse(Long orderId, String message) {
        this.orderId = orderId;
        this.message = message;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
