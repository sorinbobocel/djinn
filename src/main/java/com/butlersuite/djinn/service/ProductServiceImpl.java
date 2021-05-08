package com.butlersuite.djinn.service;

import com.butlersuite.djinn.exception.ExistingElementException;
import com.butlersuite.djinn.model.Product;
import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.repository.ProductRepository;
import com.butlersuite.djinn.utils.convert.ProductConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

   private static final String MESSAGE = "The element with the specified ID does not exist in database: ";

   private final ProductRepository productRepository;

   private final ProductConverter converter;

   @Autowired
   public ProductServiceImpl(ProductRepository productRepository,
                             ProductConverter converter) {
      this.productRepository = productRepository;
      this.converter = converter;
   }

   @Override
   public Product createProduct(ProductDTO productDTO)
         throws ExistingElementException {
      if (productRepository.existsByProductCode(productDTO.getProductCode())) {
         throw new ExistingElementException("A product with the specified " +
               "product code: " + productDTO.getProductCode() + " is already in database.");
      } else {
         return productRepository.save(converter.toEntity(productDTO));
      }
   }

   @Override
   public ProductDTO readProduct(Integer productId) {
      if (productRepository.existsById(productId)) {
         return converter.toDTO(productRepository.findById(productId).get());
      } else {
         throw new NoSuchElementException(MESSAGE + productId);
      }
   }

   @Override
   public List<ProductDTO> readAllProducts() {
      return productRepository.findAll().stream()
            .map(converter::toDTO)
            .collect(Collectors.toList());
   }

   @Override
   public Product saveProduct(ProductDTO productDTO) {
      Optional <Product> optionalProduct =
            productRepository.findProductByProductCode(productDTO.getProductCode());
     if (optionalProduct.isPresent()) {
        var product = optionalProduct.get();
         product.setName(productDTO.getName());
         product.setCategory(productDTO.getCategory());
         product.setStockQuantity(productDTO.getStockQuantity());
         product.setUnitPrice(productDTO.getUnitPrice());
         return productRepository.save(product);
      } else {
         return productRepository.save(converter.toEntity(productDTO));
      }
   }

   @Override
   public void activateProduct(Integer productId) {
      Optional<Product> optionalProduct = productRepository.findById(productId);
      if (optionalProduct.isPresent()) {
         var product = optionalProduct.get();
         product.setActive(true);
         productRepository.save(product);
      } else {
         throw new NoSuchElementException(MESSAGE + productId);
      }
   }

   @Override
   public void inactivateProduct(Integer productId) throws NoSuchElementException {
      Optional<Product> optionalProduct = productRepository.findById(productId);
      if (optionalProduct.isPresent()) {
         var product = optionalProduct.get();
         product.setActive(false);
         productRepository.save(product);
      } else {
         throw new NoSuchElementException(MESSAGE + productId);
      }
   }

   public void deleteProduct(Integer productId) {
      if (productRepository.existsById(productId)) {
         productRepository.deleteById(productId);
      } else {
         throw new NoSuchElementException(MESSAGE + productId);
      }
   }
}
