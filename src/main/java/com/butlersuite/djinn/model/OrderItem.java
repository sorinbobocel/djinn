package com.butlersuite.djinn.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class OrderItem {

   @Id
   @GeneratedValue
   private Long orderItemId;

   private String productCode;

   private String itemName;

   private BigDecimal itemPrice;

   private int itemQuantity;

   private BigDecimal orderLineAmount;

   @ManyToOne
   @JoinColumn(name = "order_id")
   private OrderSheet order;
}
