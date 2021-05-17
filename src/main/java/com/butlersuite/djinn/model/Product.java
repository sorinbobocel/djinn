package com.butlersuite.djinn.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCT")
@Data
public class Product {

   @Id
   @GeneratedValue(strategy= GenerationType.IDENTITY)
   @Column(name = "id")
   private Long productId;

   @Column(name = "category")
   @Enumerated(EnumType.STRING)
   private ProductCategory category;

   @Column(name = "name")
   private String name;

@Column(name = "quantity")
   private int stockQuantity;

   @Column(name = "price")
   private BigDecimal unitPrice;

   public Product(Long productId, ProductCategory category, String name, int stockQuantity, BigDecimal unitPrice) {
      this.productId = productId;
      this.category = category;
      this.name = name;
      this.stockQuantity = stockQuantity;
      this.unitPrice = unitPrice;
   }

   public Product() {
   }
}
