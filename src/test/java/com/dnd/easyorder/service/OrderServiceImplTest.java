package com.dnd.easyorder.service;

import com.dnd.easyorder.entity.Customer;
import com.dnd.easyorder.entity.Order;
import com.dnd.easyorder.model.PlaceOrderRequest;
import com.dnd.easyorder.model.PlaceOrderResponse;
import com.dnd.easyorder.repo.CustomerRepo;
import com.dnd.easyorder.repo.OrderItemRepo;
import com.dnd.easyorder.repo.OrderRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private OrderRepo orderRepo;

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private OrderItemRepo orderItemRepo;

    @InjectMocks
    private OrderServiceImpl orderService;

    private PlaceOrderRequest request;
    private Customer existingCustomer;

    @BeforeEach
    void setUp() {

        request = new PlaceOrderRequest();

        request.setCustomerName("Jacob Taylor");
        request.setPhone("2195551001");
        request.setPickupDate(LocalDate.of(2026, 9, 30));
        request.setSummaryNotes("Test order");
        request.setItems(new ArrayList<>());

        existingCustomer = new Customer();
        existingCustomer.setId(1L);
        existingCustomer.setName("Jacob Taylor");
        existingCustomer.setPhone("2195551001");
    }

    @Test
    void placeOrder_shouldSaveOrderForExistingCustomer() {

        // Arrange
        when(customerService.getCustomerByPhone("2195551001"))
                .thenReturn(existingCustomer);

        when(orderRepo.save(any(Order.class)))
                .thenAnswer(invocation -> {
                    Order order = invocation.getArgument(0);
                    order.setId(100L);
                    return order;
                });

        when(orderItemRepo.saveAll(anyList()))
                .thenReturn(new ArrayList<>());

        // Act
        PlaceOrderResponse response =
                orderService.placeOrder(request);

        // Assert
        assertNotNull(response);
        assertEquals(100L, response.getOrderId());
        assertEquals("NEW", response.getStatus());
        assertEquals(
                "Order #100 Saved Successfully",
                response.getMessage()
        );

        verify(orderRepo, times(1))
                .save(any(Order.class));

        verify(customerRepo, never())
                .save(any(Customer.class));

        verify(orderItemRepo, times(1))
                .saveAll(anyList());
    }
}