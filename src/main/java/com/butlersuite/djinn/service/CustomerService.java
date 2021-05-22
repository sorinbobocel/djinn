package com.butlersuite.djinn.service;

import com.butlersuite.djinn.dto.CustomerDTO;
import com.butlersuite.djinn.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public interface CustomerService {

   Customer createCustomer(CustomerDTO customerDTO);

   CustomerDTO getCustomer(Long customerId) throws NoSuchElementException;

   List<CustomerDTO> getAllCustomers();

   CustomerDTO updateCustomer(CustomerDTO customerDTO);
}
