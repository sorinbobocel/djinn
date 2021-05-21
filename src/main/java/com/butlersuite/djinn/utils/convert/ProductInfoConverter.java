package com.butlersuite.djinn.utils.convert;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.model.ProductInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductInfoConverter implements Transformer<ProductInfo, ProductDTO> {

   @Override
   public ProductInfo toEntity(ProductDTO productDTO) {
      ProductInfo productInfo = new ProductInfo();
      BeanUtils.copyProperties(productDTO, productInfo, "category", "stockQuantity");
      return productInfo;
   }

   @Override
   public ProductDTO toDTO(ProductInfo productInfo) {
      ProductDTO productDTO = new ProductDTO();
      BeanUtils.copyProperties(productInfo, productDTO);
      return productDTO;
   }
}
