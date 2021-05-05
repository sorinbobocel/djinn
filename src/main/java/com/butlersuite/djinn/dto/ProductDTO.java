package com.butlersuite.djinn.dto;

import com.butlersuite.djinn.model.ProductCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ProductDTO {

   private String productCode;

   private ProductCategory category;

   private String name;

   private int unitsPerBox;

   private int quantity;

   private BigDecimal unitPrice;
}
