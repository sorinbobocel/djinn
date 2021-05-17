package com.butlersuite.djinn.controller;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

   @Autowired
   private ProductService productService;

   @GetMapping
   public ResponseEntity<List<ProductDTO>> listProducts() {
      return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
   }

   @PostMapping
   public ResponseEntity<String> addProduct(@RequestBody ProductDTO productDTO) {
      productService.createProduct(productDTO);
      return new ResponseEntity<>("Product created successfully.", HttpStatus.ACCEPTED);
   }
}
