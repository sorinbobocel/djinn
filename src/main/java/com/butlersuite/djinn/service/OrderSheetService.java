package com.butlersuite.djinn.service;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.model.OrderSheet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderSheetService {

   OrderSheet getOrder(Integer customerId);

   List<OrderSheet> getAllOrdersFromCustomer(Integer customerId);

   OrderSheet addItemToOrder(Integer customerId, ProductDTO productDTO);

   OrderSheet createNewOrder(Integer customerId);
}
