package com.butlersuite.djinn.controller;

import com.butlersuite.djinn.dto.CustomerDTO;
import com.butlersuite.djinn.exception.ExistingElementException;
import com.butlersuite.djinn.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

   @Autowired
   private CustomerService customerService;

   @PostMapping
   public ResponseEntity addNewCustomer(@RequestBody CustomerDTO customerDTO) {
      try {
         customerService.createCustomer(customerDTO);
         return new ResponseEntity("New customer created.", HttpStatus.CREATED);
      } catch (ExistingElementException exception) {
         return new ResponseEntity(exception.getMessage(), HttpStatus.CONFLICT);
      }
   }
}
