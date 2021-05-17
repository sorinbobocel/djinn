package com.butlersuite.djinn.utils.convert;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.model.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter implements Transformer<Product , ProductDTO> {

   @Override
   public Product toEntity(ProductDTO productDTO) {
      var product = new Product();
      BeanUtils.copyProperties(productDTO, product);
      return product;
   }

   @Override
   public ProductDTO toDTO(Product product) {
      var productDTO = new ProductDTO();
      BeanUtils.copyProperties(product, productDTO);
      return productDTO;
   }
}
