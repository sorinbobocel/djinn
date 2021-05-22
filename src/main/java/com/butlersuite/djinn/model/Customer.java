package com.butlersuite.djinn.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "CUSTOMER")
@Data
public class Customer {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long customerId;

   @Column(name = "name")
   @NotNull
   private String companyName;

   @Column(name = "email")
   @Email
   @NotNull
   private String customerEmail;

   @Column(name = "password")
   private String customerPassword;

   @Embedded
   private CustomerDetails customerDetails;

   @JsonManagedReference
   @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Cart> carts = new ArrayList<>();

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Customer customer = (Customer) o;
      return companyName.equals(customer.companyName) && customerEmail.equals(customer.customerEmail) && customerPassword.equals(customer.customerPassword) && customerDetails.equals(customer.customerDetails);
   }

   @Override
   public int hashCode() {
      return Objects.hash(companyName, customerDetails);
   }
}
