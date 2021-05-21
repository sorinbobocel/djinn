package com.butlersuite.djinn.service.impl;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.model.Product;
import com.butlersuite.djinn.repository.ProductRepository;
import com.butlersuite.djinn.utils.convert.ProductConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements com.butlersuite.djinn.service.ProductService {

   private final String NO_PRODUCT = "Product is not present in database.";

   private ProductRepository productRepository;

   private ProductConverter converter;

   @Autowired
   public ProductServiceImpl(ProductRepository productRepository, ProductConverter converter) {
      this.productRepository = productRepository;
      this.converter = converter;
   }

   @Override
   public Product createProduct(ProductDTO productDTO) {
      return productRepository.save(converter.toEntity(productDTO));
   }

   @Override
   public ProductDTO readProduct(Long productId) {
      Optional<Product> product = productRepository.findById(productId);
      if (product.isPresent()) {
         return converter.toDTO(product.get());
      } else {
         throw new NoSuchElementException(NO_PRODUCT);
      }
   }

   @Override
   public List<ProductDTO> getAllProducts() {
      return productRepository.findAll().stream()
            .filter(product -> product.getStockQuantity() > 0)
            .map(converter::toDTO)
            .collect(Collectors.toList());
   }

   @Override
   public void increaseProductStock(Long productId, int quantity) {
      var optionalProduct = productRepository.findById(productId);
      if (optionalProduct.isPresent()) {
         var product = optionalProduct.get();
         product.increaseProductStock(quantity);
         productRepository.save(product);
      } else {
         throw new NoSuchElementException(NO_PRODUCT);
      }
   }

   @Override
   public void decreaseProductStock(Long productId, int quantity) {
      var optionalProduct = productRepository.findById(productId);
      if (optionalProduct.isPresent()) {
         var product = optionalProduct.get();
         product.decreaseProductStock(quantity);
         productRepository.save(product);
      } else {
         throw new NoSuchElementException(NO_PRODUCT);
      }
   }
}
