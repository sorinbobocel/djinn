package com.butlersuite.djinn.model;

import com.butlersuite.djinn.exception.InsufficientStockException;
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
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCT")
@Data
public class Product {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long productId;

   @Column(name = "category")
   @Enumerated(EnumType.STRING)
   private ProductCategory category;

   @Column(name = "name")
   @NotNull
   private String name;

   @Column(name = "quantity")
   private int stockQuantity;

   @Column(name = "price")
   @NotNull
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

   public void increaseProductStock(int quantity) {
      this.setStockQuantity(this.getStockQuantity() + quantity);
   }

   public void decreaseProductStock(int quantity) throws InsufficientStockException {
      if (quantity <= this.stockQuantity) {
         this.setStockQuantity(this.getStockQuantity() - quantity);
      } else {
         throw new InsufficientStockException("The quantity in stock is not enough to complete the sale. Available quantity : " + this.stockQuantity);
      }
   }
}
