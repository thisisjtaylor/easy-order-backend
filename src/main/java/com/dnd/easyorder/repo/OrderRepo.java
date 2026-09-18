package com.dnd.easyorder.repo;

import com.dnd.easyorder.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("SELECT o FROM Order o WHERE o.customer.id  = :customer_id ORDER BY o.id DESC")
    public List<Order> findOrderByCustomer(@Param("customer_id") long customer_id);
}
