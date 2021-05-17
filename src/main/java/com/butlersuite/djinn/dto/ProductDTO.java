package com.butlersuite.djinn.dto;

import com.butlersuite.djinn.model.ProductCategory;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

   private Long productId;

   private ProductCategory category;

   private String name;

   private int stockQuantity;

   private BigDecimal unitPrice;
}
