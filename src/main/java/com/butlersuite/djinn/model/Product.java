package com.butlersuite.djinn.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
public class Product {

   @Id
   @GeneratedValue(strategy= GenerationType.IDENTITY)
   private Integer productId;

   @Column(name = "code")
   private String productCode;

   @Column
   @Enumerated(EnumType.STRING)
   private ProductCategory category;

   @Column(name = "name")
   private String name;

   @Column(name = "quantity")
   private int stockQuantity;

   @Column(name = "price")
   private BigDecimal unitPrice;

   @Column(name = "active")
   private boolean isActive;

   public Product(Integer productId, String productCode, ProductCategory category,
                  String name, int stockQuantity, BigDecimal unitPrice, boolean isActive) {
      this.productId = productId;
      this.productCode = productCode;
      this.category = category;
      this.name = name;
      this.stockQuantity = stockQuantity;
      this.unitPrice = unitPrice;
      this.isActive= isActive();
   }
}
