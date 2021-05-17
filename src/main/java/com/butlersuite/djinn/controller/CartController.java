package com.butlersuite.djinn.controller;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.model.Cart;
import com.butlersuite.djinn.model.Item;
import com.butlersuite.djinn.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/order")
public class CartController {

   @Autowired
   private CartService cartService;

   static class ProductAndQuantity {
      public ProductDTO productDTO;
      public int quantity;
   }

   @GetMapping("/{customerId}")
   public ResponseEntity<Object> getCart(@PathVariable Long customerId) {
      try {
         return new ResponseEntity<>(cartService.getCart(customerId), HttpStatus.FOUND);
      } catch (NoSuchElementException exception) {
         return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
      }
   }

   @PostMapping("/{customerId}")
   public ResponseEntity<String> addItem(@PathVariable Long customerId, @RequestBody ProductAndQuantity productAndQuantity) {
      cartService.addToCart(customerId, productAndQuantity.productDTO, productAndQuantity.quantity);
      return new ResponseEntity<>("Product added to cart.", HttpStatus.CREATED);
   }

   @PutMapping("/{customerId}")
   public ResponseEntity<String> removeItem(@PathVariable Long customerId, @RequestBody Item item) {
      cartService.removeFromCart(customerId, item);
      return new ResponseEntity<>("Item removed from cart.", HttpStatus.GONE);
}

@DeleteMapping("/{customerId}")
public ResponseEntity<String> cancelOrder(@PathVariable Long customerId) {
   try {
      cartService.destroyCart(customerId);
      return new ResponseEntity<>("Order canceled.", HttpStatus.GONE);
   } catch (NoSuchElementException exception) {
      return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
   }
}

   @PostMapping("/{customerId}/validate")
   public ResponseEntity validateOrder(@PathVariable Long customerId) {
      cartService.validateCart(customerId);
      return new ResponseEntity("Validated successfully", HttpStatus.CREATED);
   }
}
