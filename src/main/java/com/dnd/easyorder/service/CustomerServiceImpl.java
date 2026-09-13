package com.dnd.easyorder.service;

import com.dnd.easyorder.entity.Customer;
import com.dnd.easyorder.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public Customer getCustomerByPhone(String phone) {
        Customer c = customerRepo.findCustomerByPhone(phone);
        return customerRepo.findCustomerByPhone(phone);
    }
}
