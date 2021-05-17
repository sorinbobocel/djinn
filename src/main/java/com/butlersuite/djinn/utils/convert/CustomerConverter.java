package com.butlersuite.djinn.utils.convert;

import com.butlersuite.djinn.dto.CustomerDTO;
import com.butlersuite.djinn.model.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter implements Transformer<Customer, CustomerDTO> {

   @Override
   public Customer toEntity(CustomerDTO customerDTO) {
      var customer = new Customer();
      BeanUtils.copyProperties(customerDTO, customer);
      return customer;
   }

   @Override
   public CustomerDTO toDTO(Customer customer) {
      var customerDTO = new CustomerDTO();
      BeanUtils.copyProperties(customer, customerDTO, "customerId");
      return customerDTO;
   }
}
