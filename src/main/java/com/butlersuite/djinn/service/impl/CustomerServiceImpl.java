package com.butlersuite.djinn.service.impl;

import com.butlersuite.djinn.dto.CustomerDTO;
import com.butlersuite.djinn.exception.ExistingElementException;
import com.butlersuite.djinn.model.Customer;
import com.butlersuite.djinn.repository.CustomerRepository;
import com.butlersuite.djinn.service.CustomerService;
import com.butlersuite.djinn.utils.convert.CustomerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

   private CustomerRepository customerRepository;

   private CustomerConverter converter;

   @Autowired
   public CustomerServiceImpl(CustomerRepository customerRepository, CustomerConverter converter) {
      this.customerRepository = customerRepository;
      this.converter = converter;
   }

   @Override
   public Customer createCustomer(CustomerDTO customerDTO) {
      var customer = customerRepository.findByCompanyName(customerDTO.getCompanyName());
      if (customer.isEmpty()) {
         return customerRepository.save(converter.toEntity(customerDTO));
      } else {
         throw new ExistingElementException("This company name: " + customer.get().getCompanyName().toUpperCase() + "  is already in database. Please specify the exact workstation.");
      }
   }

   @Override
   public Customer getCustomer(Long customerId) throws NoSuchElementException {
      Optional<Customer>optionalCustomer = customerRepository.findById(customerId);
      if(optionalCustomer.isPresent()) {
         return optionalCustomer.get();
      } else {
         throw new NoSuchElementException("The required customer is not present in database.");
      }
   }

   public List<CustomerDTO> getAllCustomers() {
      return customerRepository.findAll().stream()
            .map(converter::toDTO)
            .collect(Collectors.toList());
   }
}
