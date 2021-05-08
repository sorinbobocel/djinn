package com.butlersuite.djinn.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ORDER_ITEM")
@Data
public class OrderItem {

   @Id
   @GeneratedValue
   private Long orderItemId;

   private String productCode;

   private ProductCategory category;

   private String name;

   private int quantity;

   private BigDecimal unitPrice;

   private BigDecimal orderLineAmount;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "order_id", nullable = false)
   @JsonBackReference
   private OrderSheet order;
}
