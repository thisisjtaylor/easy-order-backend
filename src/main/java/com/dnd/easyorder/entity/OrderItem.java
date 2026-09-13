package com.dnd.easyorder.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @Column(name = "product_name", nullable = true)
    private String productName;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "note")
    private String note;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "sausage_type")
    private String sausageType;

    @Column(name = "sausage_form")
    private String sausageForm;

    @Column(name = "fennel")
    private String fennel;

    @Column(name = "add_cheese")
    private Boolean addCheese;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order;

    public OrderItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSausageType() {
        return sausageType;
    }

    public void setSausageType(String sausage_type) {
        this.sausageType = sausage_type;
    }

    public String getSausageForm() {
        return sausageForm;
    }

    public void setSausageForm(String sausage_form) {
        this.sausageForm = sausage_form;
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

    public void setAddCheese(Boolean add_cheese) {
        this.addCheese = add_cheese;
    }
}