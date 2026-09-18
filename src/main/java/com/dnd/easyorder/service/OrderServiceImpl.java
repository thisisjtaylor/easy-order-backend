package com.dnd.easyorder.service;

import com.dnd.easyorder.entity.Customer;
import com.dnd.easyorder.entity.Order;
import com.dnd.easyorder.entity.OrderItem;
import com.dnd.easyorder.model.*;
import com.dnd.easyorder.repo.CustomerRepo;
import com.dnd.easyorder.repo.OrderItemRepo;
import com.dnd.easyorder.repo.OrderRepo;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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

        Customer reqCustomer = this.mapToCustomer(request);

        if (customer == null) {
            customer = customerRepo.save(reqCustomer);
        }else if(!Objects.equals(customer.getName(), reqCustomer.getName())) {
            customer.setName(reqCustomer.getName());
            customerRepo.updateCustomerName(customer.getId(), customer.getName());
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

    @Override
    public List<OrderHistoryResponse> searchOrder(OrderSearchRequest request) {
        Specification<Order> specification =
                this.search(request);
        List<Order> orderList = orderRepo.findAll(specification);
        List<OrderHistoryResponse> response = new ArrayList<>();

        for(Order order : orderList){
            response.add(this.mapOrderToResponse(order));
        }
        return response;
    }

    public static Specification<Order> search(
            OrderSearchRequest request) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            Join<Order, Customer> customer =
                    root.join("customer", JoinType.INNER);

            Join<Order, OrderItem> items =
                    root.join("items", JoinType.INNER);

            // ORDER
            if (request.getOrderId() != null) {
                predicates.add(
                        cb.equal(
                                root.get("id"),
                                request.getOrderId()
                        )
                );
            }

            if (request.getPickupDate() != null) {
                predicates.add(
                        cb.equal(
                                root.get("pickupDate"),
                                request.getPickupDate()
                        )
                );
            }

            if (request.getStatus() != null) {
                predicates.add(
                        cb.equal(
                                root.get("status"),
                                request.getStatus()
                        )
                );
            }
            if (request.getSummaryNotes() != null) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("summaryNotes")),
                                "%" + request.getSummaryNotes().toLowerCase() + "%"
                        )
                );
            }

            // CUSTOMER
            if (request.getCustomerName() != null) {
                predicates.add(
                        cb.like(
                                cb.lower(customer.get("name")),
                                "%" + request.getCustomerName().toLowerCase() + "%"
                        )
                );
            }

            if (request.getPhone() != null) {
                predicates.add(
                        cb.equal(
                                customer.get("phone"),
                                request.getPhone()
                        )
                );
            }

            // ORDER ITEM
            if (request.getCategory() != null) {
                predicates.add(
                        cb.equal(
                                items.get("category"),
                                request.getCategory()
                        )
                );
            }

            if (request.getProduct() != null) {
                predicates.add(
                        cb.equal(
                                items.get("productName"),
                                request.getProduct()
                        )
                );
            }

            if (request.getQuantity() != null) {
                predicates.add(
                        cb.equal(
                                items.get("quantity"),
                                request.getQuantity()
                        )
                );
            }

            if (request.getUnit() != null) {
                predicates.add(
                        cb.equal(
                                items.get("unit"),
                                request.getUnit()
                        )
                );
            }

            if (request.getNote() != null) {
                predicates.add(
                        cb.like(
                                cb.lower(items.get("note")),
                                "%" + request.getNote().toLowerCase() + "%"
                        )
                );
            }

            // SAUSAGE
            if (request.getType() != null) {
                predicates.add(
                        cb.equal(
                                items.get("sausageType"),
                                request.getType()
                        )
                );
            }

            if (request.getForm() != null) {
                predicates.add(
                        cb.equal(
                                items.get("sausageForm"),
                                request.getForm()
                        )
                );
            }

            if (request.getFennel() != null) {
                predicates.add(
                        cb.equal(
                                items.get("fennel"),
                                request.getFennel()
                        )
                );
            }

            if (request.getCheese() != null) {
                predicates.add(
                        cb.equal(
                                items.get("addCheese"),
                                request.getCheese()
                        )
                );
            }

            query.distinct(true);

            return cb.and(
                    predicates.toArray(new Predicate[0])
            );
        };
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
