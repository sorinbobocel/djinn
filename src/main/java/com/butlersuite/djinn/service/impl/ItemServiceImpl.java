package com.butlersuite.djinn.service.impl;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.model.Item;
import com.butlersuite.djinn.repository.ItemRepository;
import com.butlersuite.djinn.utils.convert.ProductConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ItemServiceImpl implements com.butlersuite.djinn.service.ItemService {

   private ItemRepository itemRepository;

   private ProductConverter converter;

   @Autowired
   public ItemServiceImpl(ItemRepository itemRepository, ProductConverter converter) {
      this.itemRepository = itemRepository;
      this.converter = converter;
   }

   @Override
   public Item generateItem(ProductDTO productDTO, int quantity) {
      var item = new Item();
      item.setProduct(converter.toEntity(productDTO));
      item.setItemQuantity(quantity);
      item.setItemValue(productDTO.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));
      return itemRepository.save(item);
   }

   @Override
   public void deleteItem(Item item) {
      itemRepository.delete(item);
   }
}
