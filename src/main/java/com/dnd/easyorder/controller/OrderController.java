package com.dnd.easyorder.controller;

import com.dnd.easyorder.model.*;
import com.dnd.easyorder.service.CustomerService;
import com.dnd.easyorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/order-service")
public class OrderController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/getOrderHistory")
    public ResponseEntity<?> getOrderHistory(@RequestParam String phone) {

        try {
            if(customerService.getCustomerByPhone(phone) != null){
                List<OrderHistoryResponse> orders =
                        orderService.getOrderHistory(phone);
                return ResponseEntity.ok(orders);
            }else{
                return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiMessageResponse(
                            "No customer found with phone number " + phone
                    ));
            }
        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<PlaceOrderResponse> placeOrder(@RequestBody PlaceOrderRequest request){
        PlaceOrderResponse response =
                orderService.placeOrder(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/orderSearch")
    public ResponseEntity<?> orderSearch(@RequestBody OrderSearchRequest request){
        System.out.println(request);
        return null;
    }
}
