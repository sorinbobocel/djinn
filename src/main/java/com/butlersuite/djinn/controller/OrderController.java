package com.butlersuite.djinn.controller;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.model.OrderSheet;
import com.butlersuite.djinn.service.OrderSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("/orders")
public class OrderController {

   @Autowired
   private OrderSheetService orderSheetService;

   @PutMapping
   public ResponseEntity<OrderSheet> addItemToOrder(UUID customerId, ProductDTO productDTO) {
      return new ResponseEntity(orderSheetService.addItemToOrder(customerId, productDTO), HttpStatus.OK);
   }
}
