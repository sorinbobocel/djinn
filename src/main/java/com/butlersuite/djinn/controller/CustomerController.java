package com.butlersuite.djinn.controller;

import com.butlersuite.djinn.dto.CustomerDTO;
import com.butlersuite.djinn.exception.ExistingElementException;
import com.butlersuite.djinn.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/customer")
public class CustomerController {

   @Autowired
   private CustomerService customerService;

   @PostMapping
   public ResponseEntity<String> registerNewCustomer(@RequestBody CustomerDTO customerDTO) {
      try {
         customerService.createCustomer(customerDTO);
         return new ResponseEntity<>("New customer created.", HttpStatus.CREATED);
      } catch (ExistingElementException exception) {
         return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
      }
   }

   @GetMapping
   public ResponseEntity<Object> getCustomer(@PathVariable Long customerId) {
      try {
         return new ResponseEntity<>(customerService.getCustomer(customerId), HttpStatus.FOUND);
      } catch (NoSuchElementException exception) {
         return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
      }
   }

   @GetMapping("/admin/customers")
   public ResponseEntity<List> getAllCustomers() {
      return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
   }

   @PutMapping
   public ResponseEntity<Object> updateCustomer(@RequestBody CustomerDTO customerDTO) {
      try {
         return new ResponseEntity<>(customerService.updateCustomer(customerDTO), HttpStatus.OK);
      } catch (NoSuchElementException exception) {
         return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
      }
   }
}
