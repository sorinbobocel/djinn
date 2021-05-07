package com.butlersuite.djinn.model;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ORDER_SHEET")
@Getter
@ToString
public class OrderSheet {

   public static class Builder {

      private UUID customerId;
      private String orderDate;
      private List<OrderItem> itemsList;
      private BigDecimal totalAmount;
      private OrderStatus orderStatus;

      public Builder() {
      }

      public OrderSheet build() {
         return new OrderSheet(this);
      }

      public Builder customerId(UUID customerId) {
         this.customerId = customerId;
         return this;
      }

      public Builder orderDate(String orderDate) {
         this.orderDate = orderDate;
         return this;
      }

      public Builder itemsList(List<OrderItem> itemsList) {
         this.itemsList = itemsList;
         return this;
      }

      public Builder totalAmount(BigDecimal totalAmount) {
         this.totalAmount = totalAmount;
         return this;
      }

      public Builder orderStatus (OrderStatus orderStatus) {
         this.orderStatus = orderStatus;
         return this;
      }
   }

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "order_id")
   private Long orderId;

   @Column(name = "customer_id")
   private UUID customerId;

   @Column(name = "date")
   private String orderDate;

   @OneToMany(mappedBy = "order")
   private List<OrderItem> itemsList;

   @Column(name = "total")
   private BigDecimal totalAmount;

   @Column(name = "status")
   @Enumerated(EnumType.STRING)
   private OrderStatus orderStatus;

   private OrderSheet(Builder builder) {
      this.customerId = builder.customerId;
      this.orderDate = builder.orderDate;
      this.itemsList = builder.itemsList;
      this.totalAmount = builder.totalAmount;
      this.orderStatus = builder.orderStatus;
   }
}
