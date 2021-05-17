package com.butlersuite.djinn.service;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.model.Item;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {

   Item generateItem(ProductDTO productDTO, int quantity);

   void deleteItem(Item item);
}
