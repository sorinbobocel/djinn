package com.butlersuite.djinn.utils.convert;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.model.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter implements Transformer<Product, ProductDTO> {

   @Override
   public ProductDTO toDTO(Product product) {
      ProductDTO productDTO = new ProductDTO();
      BeanUtils.copyProperties(product, productDTO, "productId");
      return productDTO;
   }

   @Override
   public Product toEntity(ProductDTO productDTO) {
      Product product = new Product();
      BeanUtils.copyProperties(productDTO, product, "orderQuantity");
      return product;
   }
}
