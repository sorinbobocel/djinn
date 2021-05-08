package com.butlersuite.djinn.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER")
@Data
public class Customer {

   @Id
   @GeneratedValue(strategy = GenerationType.TABLE)
   @Column(name = "customer_id")
   private Integer customerId;

   @Column(name = "name")
   private String customerName;

   @Column(name = "email")
   private String customerEmail;

   @Column(name = "password")
   private String customerPassword;

   @Column(name = "phone")
   private String customerPhone;
}
