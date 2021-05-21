package com.butlersuite.djinn.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CART")
@Getter
@Setter
@NoArgsConstructor
public class Cart {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "cart_id")
   private Long cartId;

   @JsonBackReference
   @ManyToOne(fetch = FetchType.LAZY)
   private Customer customer;

   @Column(name = "date")
   private String creationDate;

   @JsonManagedReference
   @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Item> itemsList = new ArrayList<>();

   @Column(name = "total")
   private BigDecimal totalAmount;

   @Column
   @NotNull
   private Status status;

   public Cart(Customer customer, String creationDate, List<Item> itemsList, BigDecimal totalAmount, Status status) {
      this.customer = customer;
      this.creationDate = creationDate;
      this.itemsList = itemsList;
      this.totalAmount = totalAmount;
      this.status = status;
   }

   public Cart(Long cartId, Customer customer, String creationDate, List<Item> itemsList, BigDecimal totalAmount, Status status) {
      this.cartId = cartId;
      this.customer = customer;
      this.creationDate = creationDate;
      this.itemsList = itemsList;
      this.totalAmount = totalAmount;
      this.status = status;
   }

   @Override
   public String toString() {
      return "Cart{" +
            "cartId=" + cartId +
            ", customer=" + customer +
            ", creationDate=" + creationDate +
            ", itemsList=" + itemsList +
            ", totalAmount=" + totalAmount +
            ", status=" + status +
            '}';
   }

   public void addItem(Item item) {
      this.itemsList.add(item);
      item.setCart(this);
      this.setTotalAmount(this.getTotalAmount().add(item.getItemValue()));
   }

   public void removeItem(Item item) {
      this.itemsList.remove(item);
      this.setTotalAmount(this.getTotalAmount().subtract(item.getItemValue()));
   }
}
