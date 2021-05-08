package com.butlersuite.djinn.controller;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.service.OrderSheetService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

   @Autowired
   private OrderSheetService orderSheetService;

   @PostMapping
   public ResponseEntity addItemToOrder(@RequestParam Integer customerId, @RequestBody ProductDTO productDTO) {
      return new ResponseEntity(orderSheetService.addItemToOrder(customerId, productDTO), HttpStatus.OK);
   }
}
