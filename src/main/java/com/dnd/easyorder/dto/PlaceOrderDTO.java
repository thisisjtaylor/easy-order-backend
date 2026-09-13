package com.dnd.easyorder.dto;

import com.dnd.easyorder.entity.Customer;
import com.dnd.easyorder.entity.Order;
import com.dnd.easyorder.entity.OrderItem;
import com.dnd.easyorder.model.OrderItemRequest;
import com.dnd.easyorder.model.PlaceOrderRequest;

import java.util.ArrayList;
import java.util.List;

public class PlaceOrderDTO {

    public Customer mapToCustomer(PlaceOrderRequest request){
        Customer customer = new Customer();
        customer.setName(request.getCustomerName());
        customer.setPhone(request.getPhone());
        return customer;
    }

    public List<OrderItem> mapToOrderItems(List<OrderItemRequest> orderItemRequest, Order order){
        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderItemRequest i : orderItemRequest){
            OrderItem item = new OrderItem();
            item.setAddCheese(i.getCheese());
            item.setCategory(i.getCategory());
            item.setFennel(i.getFennel());
            item.setNote(i.getNote());
            item.setProductName(i.getProduct());
            item.setQuantity(i.getQuantity());
            item.setSausageForm(i.getForm());
            item.setSausageType(i.getType());
            item.setUnit(i.getUnit());
            item.setOrder(order);
            orderItems.add(item);
        }
        return orderItems;
    }

    public Order mapToOrder(PlaceOrderRequest request, Customer customer, List<OrderItem> items){
        Order order = new Order();
        order.setCustomer(customer);
        order.setItems(items);
        return null;
    }
}
