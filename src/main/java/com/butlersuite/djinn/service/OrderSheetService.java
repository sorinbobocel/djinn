package com.butlersuite.djinn.service;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.model.OrderSheet;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface OrderSheetService {

   OrderSheet createNewOrder(UUID customerId);

   OrderSheet getOrder(UUID customerId);

   OrderSheet addItemToOrder(UUID customerId, ProductDTO productDTO);
}
