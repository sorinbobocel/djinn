package com.butlersuite.djinn.service.impl;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.model.Customer;
import com.butlersuite.djinn.model.OrderItem;
import com.butlersuite.djinn.model.OrderSheet;
import com.butlersuite.djinn.repository.OrderSheetRepository;
import com.butlersuite.djinn.service.OrderItemService;
import com.butlersuite.djinn.service.OrderSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.butlersuite.djinn.model.OrderStatus.PENDING;

@Service
public class OrderSheetServiceImpl implements OrderSheetService {

   private OrderSheetRepository orderRepository;

   private OrderItemService orderItemService;

   @Autowired
   public OrderSheetServiceImpl(OrderSheetRepository orderRepository,
                                OrderItemService orderItemService) {
      this.orderRepository = orderRepository;
      this.orderItemService = orderItemService;
   }

   /**
    * Initializes a new pending Order, saving it to OrderSheetRepository.
    *
    * @param customerId is associated to the pending order through customerId
    * @return a new pending OrderSheet.
    */
   @Override
   public OrderSheet createNewOrder(UUID customerId) {
      OrderSheet newOrder = new OrderSheet.Builder()
            .customerId(customerId)
            .orderDate(LocalDateTime.now().toString())
            .itemsList(new ArrayList<>())
            .totalAmount(new BigDecimal(0))
            .orderStatus(PENDING)
            .build();
      return orderRepository.save(newOrder);
   }

   @Override
   public OrderSheet getOrder(UUID customerId) {
      OrderSheet orderSheet;
      Optional<OrderSheet> optionalOrderSheet = orderRepository
            .findOrderSheetByCustomerIdAndOrderStatus(customerId, PENDING);
      if (optionalOrderSheet.isPresent()) {
         orderSheet = optionalOrderSheet.get();
      } else {
         orderSheet = createNewOrder(customerId);
      }
      return orderSheet;
   }

   @Override
   public OrderSheet addItemToOrder(UUID customerId, ProductDTO productDTO) {
      OrderSheet currentOrder = getOrder(customerId);
      List<OrderItem> itemsList = currentOrder.getItemsList();
      List<OrderItem> duplicates = itemsList.stream()
            .filter(orderItem -> orderItem.getItemName().equals(productDTO.getName()))
            .collect(Collectors.toList());
      if (duplicates == null) {
         itemsList.add(orderItemService.createOrderItem(productDTO));
      } else {
         OrderItem duplicate = duplicates.get(0);
         orderItemService.updateOrderItemQuantity(duplicate);
      }
      return orderRepository.save(currentOrder);
   }
}

