package com.butlersuite.djinn.service;

import com.butlersuite.djinn.dto.CustomerDTO;
import com.butlersuite.djinn.model.Customer;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service

public interface CustomerService {

   Customer createCustomer(CustomerDTO customerDTO);

   Customer getCustomer(Long customerId) throws NoSuchElementException;
}
