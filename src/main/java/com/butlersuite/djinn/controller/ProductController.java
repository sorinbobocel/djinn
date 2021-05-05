package com.butlersuite.djinn.controller;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.exception.ExistingElementException;
import com.butlersuite.djinn.service.ProductService;
import com.butlersuite.djinn.utils.logging.AppLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/products")
public class ProductController {

   private AppLogger logger = AppLogger.getInstance();

   @Autowired
   private ProductService productService;

   @PostMapping
   public ResponseEntity<Object> registerProduct(@RequestBody ProductDTO productDTO) {
      try {
         productService.createProduct(productDTO);
         return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
      } catch (ExistingElementException e) {
         logger.log(AppLogger.LogLevel.INFO, LocalDateTime.now(), e.getMessage());
         return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
      }
   }

   @GetMapping("/{productId}")
   public ResponseEntity<Object> getProduct(@PathVariable Integer productId) {
      try {
         return new ResponseEntity<>(productService.readProduct(productId), HttpStatus.OK);
      } catch (NoSuchElementException e) {
         e.printStackTrace();
         return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
      }
   }
}
