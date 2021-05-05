package com.butlersuite.djinn.service;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.exception.ExistingElementException;
import com.butlersuite.djinn.model.Product;
import com.butlersuite.djinn.repository.ProductRepository;
import com.butlersuite.djinn.utils.convert.ProductConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.butlersuite.djinn.model.ProductCategory.PAINE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

   private ProductServiceImpl underTest;

   @Mock
   private ProductRepository mockProductRepository;

   @Mock
   private ProductConverter mockConverter;

   @BeforeEach
   void setUp() {
      underTest = new ProductServiceImpl(mockProductRepository, mockConverter);
   }

   @Test
   void createProduct() {

   }

   @Test
   void testIfProductIsReturnedCorrectly() {

      //given
      Product product = new Product(1, "5049382718291", PAINE,
            "Paine alba 300g", 24, 50, new BigDecimal(12.50));

      //when
      Mockito.when(mockProductRepository.existsById(1)).thenReturn(true);
      Mockito.when(mockProductRepository.findById(1)).thenReturn(Optional.of(product));
      Mockito.when(mockConverter.toDTO(product)).thenCallRealMethod();
      ProductDTO result = underTest.readProduct(1);

      //then
      Assertions.assertEquals("5049382718291", result.getProductCode());
   }

   @Test
   void verifyIfExceptionIsThrownWhenProductIsNotInDB() {

      //when
      Mockito.when(mockProductRepository.existsById(1)).thenReturn(false);

      //then
      Assertions.assertThrows(NoSuchElementException.class, ()-> {

         //when
         underTest.readProduct(1);
      });
   }

   @Test
   void readAllProducts() {
   }

   @Test
   void saveProduct() {
   }

   @Test
   void deleteProduct() {
   }
}