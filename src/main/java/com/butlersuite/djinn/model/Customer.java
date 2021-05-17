package com.butlersuite.djinn.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CUSTOMER")
@Data
public class Customer {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long customerId;

   @Column(name = "name")
   private String companyName;

   @Column(name = "email")
   private String customerEmail;

   @Column(name = "password")
   private String customerPassword;

   @Embedded
   private CustomerDetails customerDetails;

   @JsonManagedReference
   @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Cart> carts = new ArrayList<>();
}
