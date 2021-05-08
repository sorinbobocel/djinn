package com.butlersuite.djinn.utils.convert;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.model.OrderItem; 
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class OrderItemConverter {

   public OrderItem toOrderItem(ProductDTO productDTO) {
      OrderItem orderItem = new OrderItem();
      BeanUtils.copyProperties(productDTO, orderItem, "quantity", "orderLineAmount");
      return orderItem;
   }
}