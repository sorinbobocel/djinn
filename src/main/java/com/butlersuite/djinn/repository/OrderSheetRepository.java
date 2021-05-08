package com.butlersuite.djinn.repository;

import com.butlersuite.djinn.model.OrderSheet;
import com.butlersuite.djinn.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderSheetRepository extends JpaRepository <OrderSheet, Long> {

   Optional<OrderSheet> findOrderSheetByCustomerIdAndOrderStatus(Integer CustomerId, OrderStatus orderStatus);
}
