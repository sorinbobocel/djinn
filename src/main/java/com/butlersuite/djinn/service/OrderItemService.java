package com.butlersuite.djinn.service;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.model.OrderItem;
import org.springframework.stereotype.Service;

@Service
public interface OrderItemService {

   OrderItem createOrderItem(ProductDTO productDTO);

   OrderItem updateOrderItemQuantity(OrderItem orderItem);
}
