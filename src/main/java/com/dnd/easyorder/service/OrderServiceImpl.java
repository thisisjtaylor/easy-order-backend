package com.dnd.easyorder.service;

import com.dnd.easyorder.entity.Customer;
import com.dnd.easyorder.entity.Order;
import com.dnd.easyorder.entity.OrderItem;
import com.dnd.easyorder.model.*;
import com.dnd.easyorder.repo.CustomerRepo;
import com.dnd.easyorder.repo.OrderItemRepo;
import com.dnd.easyorder.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Override
    public List<OrderHistoryResponse> getOrderHistory(String phone) {

        Customer c = customerService.getCustomerByPhone(phone);

        List<Order> orders =
                orderRepo.findOrderByCustomer(c.getId());

        return orders.stream()
                .map(this::mapOrderToResponse)
                .toList();
    }

    @Transactional
    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderRequest request) {


        PlaceOrderResponse response = new PlaceOrderResponse();

        Customer customer =
                customerService.getCustomerByPhone(request.getPhone());

        if (customer == null) {
            customer = this.mapToCustomer(request);
            customer = customerRepo.save(customer);
        }

        Order order =
                this.mapToOrder(request, customer);

        order = orderRepo.save(order);

        List<OrderItem> orderItems =
                this.mapToOrderItems(request.getItems(), order);

        orderItems = orderItemRepo.saveAll(orderItems);


        response.setOrderId(order.getId());
        response.setStatus(order.getStatus());
        response.setMessage("Order #" + order.getId() + " Saved Successfully");

        return response;
    }

    private Customer mapToCustomer(PlaceOrderRequest request){
        Customer customer = new Customer();
        customer.setName(request.getCustomerName());
        customer.setPhone(request.getPhone());
        return customer;
    }

    private Order mapToOrder(PlaceOrderRequest request, Customer customer){
        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus("NEW");
        order.setPickupDate(request.getPickupDate());
        order.setSummaryNotes(request.getSummaryNotes());
        //order.setItems(items);
        return order;
    }

    private List<OrderItem> mapToOrderItems(List<OrderItemRequest> orderItemRequest, Order order){
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

    private OrderHistoryResponse mapOrderToResponse(Order order) {

        OrderHistoryResponse response = new OrderHistoryResponse();

        response.setId(order.getId());
        response.setPickupDate(order.getPickupDate());
        response.setStatus(order.getStatus());
        response.setSummaryNotes(order.getSummaryNotes());

        CustomerResponse customerResponse = new CustomerResponse();

        customerResponse.setName(order.getCustomer().getName());
        customerResponse.setPhone(order.getCustomer().getPhone());

        response.setCustomer(customerResponse);

        List<OrderItemResponse> items = order.getItems()
                .stream()
                .map(item -> {

                    OrderItemResponse itemResponse =
                            new OrderItemResponse();

                    //itemResponse.setId(item.getId());
                    itemResponse.setProductName(
                            item.getProductName()
                    );
                    itemResponse.setQuantity(
                            item.getQuantity()
                    );
                    itemResponse.setUnit(
                            item.getUnit()
                    );
                    itemResponse.setNote(
                            item.getNote()
                    );
                    itemResponse.setSausageForm(
                            item.getSausageForm()
                    );
                    itemResponse.setSausageType(
                            item.getSausageType()
                    );
                    itemResponse.setCategory(
                            item.getCategory()
                    );
                    itemResponse.setFennel(
                            item.getFennel()
                    );
                    itemResponse.setAddCheese(
                            item.getAddCheese()
                    );


                    return itemResponse;
                })
                .toList();

        response.setItems(items);

        return response;
    }
}
