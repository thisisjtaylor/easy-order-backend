package com.dnd.easyorder.model;

import java.math.BigDecimal;

public class OrderItemRequest {

    private String category;

    private String product;

    private BigDecimal quantity;

    private String unit;

    private String note;

    // SAUSAGE ONLY
    private String type;

    private String form;

    private String fennel;

    private Boolean cheese;

    public String getProduct() {
        return product;
    }

    public void setProductName(String product) {
        this.product = product;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getFennel() {
        return fennel;
    }

    public void setFennel(String fennel) {
        this.fennel = fennel;
    }

    public Boolean getCheese() {
        return cheese;
    }

    public void setCheese(Boolean cheese) {
        this.cheese = cheese;
    }
}
