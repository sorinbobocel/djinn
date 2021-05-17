package com.butlersuite.djinn.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ITEMS")
@Data
public class Item {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long itemId;

   @OneToOne
   private Product product;

   @Column(name = "quantity")
   private int itemQuantity;

   @Column(name = "amount")
   private BigDecimal itemValue;

   @JsonBackReference
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "cart_id")
   private Cart cart;

   @Override
   public String toString() {
      return "Item{" +
            "itemId=" + itemId +
            ", product=" + product +
            ", itemQuantity=" + itemQuantity +
            ", itemValue=" + itemValue +
            '}';
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Item item = (Item) o;
      return itemQuantity == item.itemQuantity && itemId.equals(item.itemId) && product.equals(item.product) && itemValue.equals(item.itemValue);
   }

   @Override
   public int hashCode() {
      return Objects.hash(itemId, product, itemQuantity, itemValue);
   }
}
