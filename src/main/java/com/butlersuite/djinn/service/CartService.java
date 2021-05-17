package com.butlersuite.djinn.service;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.model.Cart;
import com.butlersuite.djinn.model.Item;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

   Cart getCart(Long customerId);

   Cart addToCart(Long customerId, ProductDTO productDTO, int quantity);

   Cart removeFromCart(Long customerId, Item item);

   void validateCart(Long customerId);

   void destroyCart(Long customerId);
}
