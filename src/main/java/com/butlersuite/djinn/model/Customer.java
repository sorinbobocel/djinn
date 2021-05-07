package com.butlersuite.djinn.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class Customer {

   @Id
   @GeneratedValue(generator = "system-uuid")
   private UUID customerId;

   private String customerName;

   private String customerEmail;

   private String customerPassword;

   private String customerPhone;
}
