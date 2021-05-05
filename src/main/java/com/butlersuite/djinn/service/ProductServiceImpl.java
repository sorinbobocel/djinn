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
         throw new NoSuchElementException("The product with ID " +
               productId + " doesn't exist in database.");
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
      Optional<Product> product =
            productRepository.findProductByProductCode(productDTO.getProductCode());
      return product.orElseGet(() ->
            productRepository.save(converter.toEntity(productDTO)));
   }

   @Override
   public void deleteProduct(Integer productId) {
      productRepository.deleteById(productId);
   }
}
