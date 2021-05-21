package com.butlersuite.djinn.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
@ToString
public class ProductInfo {

   private Long productId;

   private String name;

   private BigDecimal unitPrice;

   public ProductInfo() {
   }

   public ProductInfo(Long productId, String name, BigDecimal unitPrice) {
      this.productId = productId;
      this.name = name;
      this.unitPrice = unitPrice;
   }
}
