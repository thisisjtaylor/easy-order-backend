package com.dnd.easyorder.repo;

import com.dnd.easyorder.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Customer findCustomerByPhone(String phone);

    @Modifying
    @Query("UPDATE Customer c SET c.name = :customer_name WHERE c.id = :customer_id")
    void updateCustomerName(@Param("customer_id") long customer_id, @Param("customer_name") String customer_name);
}
