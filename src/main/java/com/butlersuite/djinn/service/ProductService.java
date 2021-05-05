package com.butlersuite.djinn.service;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.exception.ExistingElementException;
import com.butlersuite.djinn.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
   Product createProduct(ProductDTO productDTO) throws ExistingElementException;

   ProductDTO readProduct(Integer productId);

   List<ProductDTO> readAllProducts();

   Product saveProduct(ProductDTO productDTO);

   void deleteProduct(Integer productId);
}
