package com.butlersuite.djinn.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "ITEMS")
@Getter
@Setter
@ToString
public class Item {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long itemId;

   @Embedded
   private OrderDetail orderDetail;

   @Column(name = "amount")
   @NotNull
   private BigDecimal itemValue;

   @JsonBackReference
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "cart_id")
   private Cart cart;

   @Override
   public String toString() {
      return "Item{" +
            "itemId=" + itemId +
            "orderDetail=" + orderDetail +
            ", itemValue=" + itemValue +
            '}';
   }
}
