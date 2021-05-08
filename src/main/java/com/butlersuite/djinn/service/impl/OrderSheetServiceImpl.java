package com.butlersuite.djinn.service.impl;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.model.OrderItem;
import com.butlersuite.djinn.model.OrderSheet;
import com.butlersuite.djinn.repository.OrderSheetRepository;
import com.butlersuite.djinn.service.OrderItemService;
import com.butlersuite.djinn.service.OrderSheetService;
import com.butlersuite.djinn.service.ProductService;
import com.butlersuite.djinn.utils.convert.OrderItemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.butlersuite.djinn.model.OrderStatus.PENDING;
import static com.butlersuite.djinn.model.OrderStatus.VALIDATED;

@Service
public class OrderSheetServiceImpl implements OrderSheetService {

   private OrderSheetRepository orderRepository;

   private ProductService productService;

   private OrderItemService orderItemService;

   private OrderItemConverter converter;

   @Autowired
   public OrderSheetServiceImpl(OrderSheetRepository orderRepository,
                                ProductService productService, OrderItemService orderItemService, OrderItemConverter converter) {
      this.orderRepository = orderRepository;
      this.productService = productService;
      this.orderItemService = orderItemService;
      this.converter = converter;
   }

   /**
    * Initializes a new pending Order, saving it to OrderSheetRepository.
    *
    * @param customerId is associated to the pending order through customerId
    * @return a new pending OrderSheet.
    */
   @Override
   public OrderSheet createNewOrder(Integer customerId) {
      var orderSheet = new OrderSheet();
      orderSheet.setCustomerId(customerId);
      orderSheet.setOrderDate(LocalDateTime.now().toString());
      orderSheet.setItemsSet(new HashSet<>());
      orderSheet.setOrderStatus(PENDING);
      orderSheet.setTotalAmount(new BigDecimal(0));
      return orderRepository.save(orderSheet);
   }

   @Override
   public OrderSheet getOrder(Integer customerId) {
      OrderSheet orderSheet;
      Optional<OrderSheet> optionalOrderSheet = orderRepository
            .findOrderSheetByCustomerIdAndOrderStatus(customerId, PENDING);
      orderSheet = optionalOrderSheet.orElseGet(() -> createNewOrder(customerId));
      return orderSheet;
   }

   @Override
   public List<OrderSheet> getAllOrdersFromCustomer(Integer customerId) {
      return orderRepository.findAll().stream()
            .filter(orderSheet -> orderSheet.getCustomerId().equals(customerId))
            .collect(Collectors.toList());
   }

   @Override
   public OrderSheet addItemToOrder(Integer customerId, ProductDTO productDTO) {
      var orderSheet = getOrder(customerId);
      Optional<OrderItem> duplicateItem = orderSheet.getItemsSet().stream()
            .filter(orderItem -> orderItem.getProductCode().equals(productDTO.getProductCode())).findAny();
      if (duplicateItem.isEmpty()) {
         addNewOrderLine(orderSheet, productDTO);
      } else {
         updateExistingOrderLine(orderSheet, duplicateItem.get(), productDTO);
      }
      return orderSheet;
   }

   public OrderSheet validateOrder(OrderSheet orderSheet) {
      orderSheet.setOrderStatus(VALIDATED);
      return orderRepository.save(orderSheet);
   }

   private void addNewOrderLine(OrderSheet orderSheet, ProductDTO productDTO) {
      var newItem = orderItemService.createOrderItem(orderSheet, productDTO);
      orderSheet.getItemsSet().add(newItem);
      orderSheet.setTotalAmount(orderSheet.getTotalAmount().add(newItem.getOrderLineAmount()));
      orderRepository.save(orderSheet);
   }
   private void updateExistingOrderLine(OrderSheet orderSheet, OrderItem orderItem,
                                        ProductDTO productDTO) {
      orderItemService.updateItem(orderItem, productDTO);
      orderSheet.setTotalAmount(orderSheet.getTotalAmount().add(productDTO.getUnitPrice()
            .multiply(BigDecimal.valueOf(productDTO.getOrderQuantity()))));
      orderRepository.save(orderSheet);
   }
}

