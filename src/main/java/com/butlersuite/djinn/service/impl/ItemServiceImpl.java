package com.butlersuite.djinn.service.impl;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.exception.InsufficientOrderQuantityException;
import com.butlersuite.djinn.model.Item;
import com.butlersuite.djinn.model.OrderDetail;
import com.butlersuite.djinn.repository.ItemRepository;
import com.butlersuite.djinn.service.ProductService;
import com.butlersuite.djinn.utils.convert.ProductInfoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ItemServiceImpl implements com.butlersuite.djinn.service.ItemService {

   private ProductService productService;

   private ItemRepository itemRepository;

   private ProductInfoConverter converter;


   @Autowired
   public ItemServiceImpl(ProductService productService, ItemRepository itemRepository, ProductInfoConverter converter) {
      this.productService = productService;
      this.itemRepository = itemRepository;
      this.converter = converter;
   }

   @Override
   public Item generateItem(ProductDTO productDTO, int quantity) {
      int minQuantity = setMinQuantity(productDTO);
      if (quantity >= minQuantity) {
         var item = new Item();
         OrderDetail detail = new OrderDetail();
         detail.setProductInfo(converter.toEntity(productDTO));
         detail.setQuantity(quantity);
         item.setOrderDetail(detail);
         item.setItemValue(productDTO.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));
         return itemRepository.save(item);
      } else {
         throw new InsufficientOrderQuantityException("Minimum order quantity for this product is: " + minQuantity);
      }
   }

   @Override
   public void decreaseProductStock(Item item) {
      productService.decreaseProductStock(item.getOrderDetail().getProductInfo().getProductId(),
            item.getOrderDetail().getQuantity());
   }

   @Override
   public void deleteItem(Item item) {
      itemRepository.delete(item);
   }

   private int setMinQuantity(ProductDTO productDTO) {
      String id = String.valueOf(productDTO.getProductId());

      switch (id) {

         case "1": case "2":
            return 12;
         case "3":
            return 9;
         case "4": case "5":
            return 8;
         case "6": case "7": case "8": case "9": case "10":
            return 4;
         case "11": case "12":
            return 6;
         case "13": case "14": case "15": case "20": case "21":
            return 1;
         case "16": case "17": case "18": case "19":
            return 2;
         case "22":
            return 25;

         default:
            return 0;
      }
   }
}
