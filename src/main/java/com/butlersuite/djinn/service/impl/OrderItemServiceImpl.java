package com.butlersuite.djinn.service.impl;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.model.OrderItem;
import com.butlersuite.djinn.repository.OrderItemRepository;
import com.butlersuite.djinn.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {

   private OrderItemRepository orderItemRepository;

   @Autowired
   public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
      this.orderItemRepository = orderItemRepository;
   }

   @Override
   public OrderItem createOrderItem(ProductDTO productDTO) {
      OrderItem orderItem = new OrderItem();
      orderItem.setProductCode(productDTO.getProductCode());
      orderItem.setItemName(productDTO.getName());
      orderItem.setItemPrice(productDTO.getUnitPrice());
      orderItem.setItemQuantity(productDTO.getQuantity());
      orderItem.setOrderLineAmount(productDTO.getUnitPrice()
            .multiply(BigDecimal.valueOf(productDTO.getQuantity())));
      return orderItemRepository.save(orderItem);
   }

   public OrderItem updateOrderItemQuantity(OrderItem orderItem) {
      Optional<OrderItem> optionalOrderItem =
            orderItemRepository.findOrderItemByProductCode(orderItem.getProductCode());
      if (optionalOrderItem.isPresent()) {
         var existingOrderItem = optionalOrderItem.get();
         existingOrderItem.setItemQuantity
               (existingOrderItem.getItemQuantity() + orderItem.getItemQuantity());
         return orderItemRepository.save(existingOrderItem);
      } else {
         return orderItemRepository.save(orderItem);
      }
   }
}