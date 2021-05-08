package com.butlersuite.djinn.service.impl;

import com.butlersuite.djinn.dto.CustomerDTO;
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

   private static final String MESSAGE = "The specified email does not exist in database: ";

   private CustomerRepository customerRepository;

   private CustomerConverter converter;

   @Autowired
   public CustomerServiceImpl(CustomerRepository customerRepository,
                              CustomerConverter converter) {
      this.customerRepository = customerRepository;
      this.converter = converter;
   }

   @Override
   public Customer createCustomer(CustomerDTO customerDTO) {
      return customerRepository.save(converter.toEntity(customerDTO));
   }

   @Override
   public CustomerDTO readCustomer(Integer customerId) {
      return converter.toDTO(customerRepository.getOne(customerId));
   }

   @Override
   public List<CustomerDTO> readAllCustomers() {
      return customerRepository.findAll().stream()
            .map(converter::toDTO)
            .collect(Collectors.toList());
   }

   @Override
   public Customer saveCustomer(CustomerDTO customerDTO) {
      Optional <Customer> optionalCustomer =
            customerRepository.findByCustomerEmail(customerDTO.getCustomerEmail());
      if (optionalCustomer.isPresent()) {
         var customer = optionalCustomer.get();
         customer.setCustomerName(customerDTO.getCustomerName());
         customer.setCustomerPassword(customerDTO.getCustomerPassword());
         customer.setCustomerPhone(customerDTO.getCustomerPhone());
         return customerRepository.save(customer);
      } else {
         throw new NoSuchElementException(MESSAGE + customerDTO.getCustomerEmail());
      }
   }
}
