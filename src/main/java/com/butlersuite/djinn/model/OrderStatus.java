package com.butlersuite.djinn.model;

public enum OrderStatus {

   PENDING("PENDING"),
   VALIDATED("VALIDATED"),
   CANCELLED("CANCELLED");

   private final String value;

   private OrderStatus(String value) {
      this.value = value;
   }

   @Override
   public String toString() {
      return value;
   }
}
