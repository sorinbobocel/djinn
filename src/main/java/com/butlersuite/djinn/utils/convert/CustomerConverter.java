package com.butlersuite.djinn.utils.convert;

import com.butlersuite.djinn.dto.CustomerDTO;
import com.butlersuite.djinn.model.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter implements Transformer<Customer , CustomerDTO> {

   @Override
   public CustomerDTO toDTO(Customer customer) {
      CustomerDTO customerDTO = new CustomerDTO();
      BeanUtils.copyProperties(customer, customerDTO, "customerID");
      return customerDTO;
   }

   @Override
   public Customer toEntity(CustomerDTO customerDTO) {
      Customer customer = new Customer();
      BeanUtils.copyProperties(customerDTO, customer);
      return customer;
   }
}
