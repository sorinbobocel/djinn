package com.butlersuite.djinn.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@ToString
public class CustomerDetails {

   private String registrationNumber;

   private String phone;

   private String deliveryAddress;

   public CustomerDetails() {
   }

   public CustomerDetails(String registrationNumber, String phone, String deliveryAddress) {
      this.registrationNumber = registrationNumber;
      this.phone = phone;
      this.deliveryAddress = deliveryAddress;
   }
}
