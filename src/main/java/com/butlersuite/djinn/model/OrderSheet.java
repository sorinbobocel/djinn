package com.butlersuite.djinn.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ORDER_SHEET")
@Data
public class OrderSheet {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "order_id")
   private Long orderId;

   @Column(name = "customer_id")
   private Integer customerId;

   @Column(name = "date")
   private String orderDate;

   @OneToMany(mappedBy = "order")
   @JsonManagedReference
   private Set<OrderItem> itemsSet = new HashSet<>();

   @Column(name = "total")
   private BigDecimal totalAmount;

   @Column(name = "status")
   @Enumerated(EnumType.STRING)
   private OrderStatus orderStatus;

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      OrderSheet that = (OrderSheet) o;
      return orderId.equals(that.orderId);
   }

   @Override
   public int hashCode() {
      return Objects.hash(orderId, customerId, orderDate, totalAmount, orderStatus);
   }
}
