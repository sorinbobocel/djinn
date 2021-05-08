package com.butlersuite.djinn.service.impl;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.model.OrderItem;
import com.butlersuite.djinn.model.OrderSheet;
import com.butlersuite.djinn.repository.OrderItemRepository;
import com.butlersuite.djinn.service.OrderItemService;
import com.butlersuite.djinn.utils.convert.OrderItemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderItemServiceImpl implements OrderItemService {

   private OrderItemRepository orderItemRepository;

   private OrderItemConverter converter;

   @Autowired
   public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderItemConverter converter) {
      this.orderItemRepository = orderItemRepository;
      this.converter = converter;
   }

   @Override
   public OrderItem createOrderItem(OrderSheet orderSheet, ProductDTO productDTO) {
      OrderItem orderItem = converter.toOrderItem(productDTO);
      orderItem.setQuantity(productDTO.getOrderQuantity());
      orderItem.setOrderLineAmount(productDTO.getUnitPrice()
            .multiply(BigDecimal.valueOf(productDTO.getOrderQuantity())));
      orderItem.setOrder(orderSheet);
      return orderItemRepository.save(orderItem);
   }

   @Override
   public void updateItem(OrderItem orderItem, ProductDTO productDTO) {
      orderItem.setQuantity(orderItem.getQuantity() + productDTO.getOrderQuantity());
      orderItem.setOrderLineAmount(orderItem.getOrderLineAmount()
            .add(BigDecimal.valueOf(productDTO.getOrderQuantity()).multiply(productDTO.getUnitPrice())));
      orderItemRepository.save(orderItem);
   }

   @Override
   public void deleteItem(Long orderItemId) {
      orderItemRepository.deleteById(orderItemId);
   }
}