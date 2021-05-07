package com.butlersuite.djinn.service;

import com.butlersuite.djinn.dto.CustomerDTO;
import com.butlersuite.djinn.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CustomerService {
   Customer createCustomer(CustomerDTO customerDTO);

   CustomerDTO readCustomer(UUID customerID);

   List<CustomerDTO> readAllCustomers();

   Customer saveCustomer(CustomerDTO customerDTO);
}
