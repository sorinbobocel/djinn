package com.butlersuite.djinn.service.impl;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.exception.InsufficientStockException;
import com.butlersuite.djinn.model.Cart;
import com.butlersuite.djinn.model.Customer;
import com.butlersuite.djinn.repository.CartRepository;
import com.butlersuite.djinn.service.CustomerService;
import com.butlersuite.djinn.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;

import static com.butlersuite.djinn.model.ProductCategory.COLAC;
import static com.butlersuite.djinn.model.Status.PENDING;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

   private CartServiceImpl underTest;

   @Mock
   private CartRepository mockCartRepository;

   @Mock
   private CustomerService mockCustomerService;

   @Mock
   private ItemService mockItemService;

   @BeforeEach
   void setUp() {
      underTest = new CartServiceImpl(mockCartRepository, mockCustomerService, mockItemService);
   }

   @Test
   void test_if_out_of_stock_item_throws_exception() {
      //given
      var cart = new Cart(new Customer(), "x", new ArrayList<>(), new BigDecimal(0.00), PENDING);
      var productDTO = new ProductDTO();
      productDTO.setProductId(1L);
      productDTO.setCategory(COLAC);
      productDTO.setName("COLAC 1KG");
      productDTO.setStockQuantity(0);
      productDTO.setUnitPrice(new BigDecimal(4.00));
      //then
      assertThrows(InsufficientStockException.class, () -> {
         //when
         underTest.addToCart(1L, productDTO, 30);
      });
   }

   @Test
   void test_if_negative_quantity_throws_exception() {
      //given
      var cart = new Cart(new Customer(), "x", new ArrayList<>(), new BigDecimal(0.00), PENDING);
      var productDTO = new ProductDTO();
      productDTO.setProductId(1L);
      productDTO.setCategory(COLAC);
      productDTO.setName("COLAC 1KG");
      productDTO.setStockQuantity(0);
      productDTO.setUnitPrice(new BigDecimal(4.00));
      //then
      assertThrows(IllegalArgumentException.class, () -> {
         //when
         underTest.addToCart(1L, productDTO, -30);
      });
   }

   @Test
   void getCart() {
   }

   @Test
   void removeFromCart() {
   }

   @Test
   void validateCart() {
   }

   @Test
   void destroyCart() {
   }
}