package com.butlersuite.djinn.repository;

import com.butlersuite.djinn.model.Customer;
import com.butlersuite.djinn.model.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository <Customer, Long>{

   Optional<Customer> findByCompanyName(String companyName);

   Customer findByCompanyNameAndCustomerDetails(String companyName, CustomerDetails customerDetails);
}
