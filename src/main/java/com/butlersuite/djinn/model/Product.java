package com.butlersuite.djinn.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class Product {

   @Id
   @GeneratedValue(strategy= GenerationType.IDENTITY)
   private Integer productId;

   private String productCode;

   @Enumerated(EnumType.STRING)
   private ProductCategory category;

   private String name;

   private int unitsPerBox;

   private int quantity;

   private BigDecimal unitPrice;

   public Product(Integer productId, String productCode, ProductCategory category,
                  String name, int unitsPerBox, int quantity, BigDecimal unitPrice) {
      this.productId = productId;
      this.productCode = productCode;
      this.category = category;
      this.name = name;
      this.unitsPerBox = unitsPerBox;
      this.quantity = quantity;
      this.unitPrice = unitPrice;
   }
}
