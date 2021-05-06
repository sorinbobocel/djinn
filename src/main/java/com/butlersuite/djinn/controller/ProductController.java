package com.butlersuite.djinn.controller;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.exception.ExistingElementException;
import com.butlersuite.djinn.model.Product;
import com.butlersuite.djinn.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/products")
public class ProductController {

   @Autowired
   private ProductService productService;

   @PostMapping
   public ResponseEntity<Object> registerProduct(@RequestBody ProductDTO productDTO) {
      try {
         productService.createProduct(productDTO);
         return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
      } catch (ExistingElementException e) {
         return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
      }
   }

   @GetMapping("/{productId}")
   public ResponseEntity<Object> getProduct(@PathVariable Integer productId) {
      try {
         return new ResponseEntity<>(productService.readProduct(productId), HttpStatus.FOUND);
      } catch (NoSuchElementException e) {
         return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
      }
   }

   @GetMapping
   public ResponseEntity<List<ProductDTO>> getAllProducts() {
      return new ResponseEntity<>(productService.readAllProducts(), HttpStatus.OK);
   }

   @PutMapping
   public ResponseEntity<Product> updateProduct(@RequestBody ProductDTO productDTO) {
      return new ResponseEntity<>(productService.saveProduct(productDTO), HttpStatus.OK);
   }

   @PutMapping("/{productId}/activate")
   public ResponseEntity<String> activateProduct(@PathVariable Integer productId) {
      productService.activateProduct(productId);
      return new ResponseEntity<>("Product activated successfully.", HttpStatus.OK);
   }

   @PutMapping("/{productId}/inactivate")
   public ResponseEntity<String> inactivateProduct(@PathVariable Integer productId) {
      try {
         productService.inactivateProduct(productId);
         return new ResponseEntity<>("Product inactivated successfully.", HttpStatus.OK);
      } catch (NoSuchElementException e) {
         e.printStackTrace();
         return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
      }
   }

   @DeleteMapping("/{productId}")
   public ResponseEntity<String>deleteProduct(@PathVariable Integer productId) {
      try {
         productService.deleteProduct(productId);
         return new ResponseEntity<>("Product deleted.", HttpStatus.OK);
      } catch (NoSuchElementException e) {
         return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
      }
   }
}