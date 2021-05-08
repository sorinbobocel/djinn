package com.butlersuite.djinn.service;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.model.OrderItem;
import com.butlersuite.djinn.model.OrderSheet;
import org.springframework.stereotype.Service;

@Service
public interface OrderItemService {

   OrderItem createOrderItem(OrderSheet orderSheet, ProductDTO productDTO);

   void updateItem(OrderItem orderItem, ProductDTO productDTO);

   void deleteItem(Long orderItemId);
}
