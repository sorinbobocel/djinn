package com.butlersuite.djinn.repository;

import com.butlersuite.djinn.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository <Customer, UUID>{

   Optional<Customer> findByCustomerEmail(String customerEmail);
}
