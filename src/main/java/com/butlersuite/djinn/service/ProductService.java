package com.butlersuite.djinn.service;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.exception.ExistingElementException;
import com.butlersuite.djinn.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public interface ProductService {
   Product createProduct(ProductDTO productDTO) throws ExistingElementException;

   ProductDTO readProduct(Integer productId);

   List<ProductDTO> readAllProducts();

   Product saveProduct(ProductDTO productDTO);

   void activateProduct(Integer productId);

   void inactivateProduct(Integer productId) throws NoSuchElementException;

   void deleteProduct(Integer productId);
}
