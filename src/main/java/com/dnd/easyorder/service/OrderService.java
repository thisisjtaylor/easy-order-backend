package com.dnd.easyorder.service;

import com.dnd.easyorder.entity.Order;
import com.dnd.easyorder.model.OrderHistoryResponse;
import com.dnd.easyorder.model.OrderSearchRequest;
import com.dnd.easyorder.model.PlaceOrderRequest;
import com.dnd.easyorder.model.PlaceOrderResponse;

import java.util.List;

public interface OrderService {
    public List<OrderHistoryResponse> getOrderHistory(String phone);

    public PlaceOrderResponse placeOrder(PlaceOrderRequest request);

    public List<OrderHistoryResponse> searchOrder(OrderSearchRequest request);

    public PlaceOrderResponse updateOrder(Long orderId, PlaceOrderRequest request);
}
