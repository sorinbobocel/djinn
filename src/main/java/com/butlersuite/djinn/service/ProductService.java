package com.butlersuite.djinn.service;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

   Product createProduct(ProductDTO productDTO);

   ProductDTO readProduct(Long productId);

   List<ProductDTO> getAllProducts();
}
