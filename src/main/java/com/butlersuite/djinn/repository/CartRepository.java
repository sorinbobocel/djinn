package com.butlersuite.djinn.repository;

import com.butlersuite.djinn.model.Cart;
import com.butlersuite.djinn.model.Customer;
import com.butlersuite.djinn.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

   Optional<Cart> findByCustomerAndStatus(Customer customer, Status status);
}
