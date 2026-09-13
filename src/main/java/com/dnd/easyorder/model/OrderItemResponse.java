package com.dnd.easyorder.model;

import java.math.BigDecimal;

public class OrderItemResponse {

    private String productName;

    private BigDecimal quantity;

    private String unit;

    private String note;

    private String sausageForm;

    private String sausageType;

    private String category;

    private String fennel;

    private Boolean addCheese;

    public OrderItemResponse() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSausageForm() {
        return sausageForm;
    }

    public void setSausageForm(String sausageForm) {
        this.sausageForm = sausageForm;
    }

    public String getSausageType() {
        return sausageType;
    }

    public void setSausageType(String sausageType) {
        this.sausageType = sausageType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFennel() {
        return fennel;
    }

    public void setFennel(String fennel) {
        this.fennel = fennel;
    }

    public Boolean getAddCheese() {
        return addCheese;
    }

    public void setAddCheese(Boolean addCheese) {
        this.addCheese = addCheese;
    }
}
