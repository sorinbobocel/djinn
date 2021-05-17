package com.butlersuite.djinn.model;

public enum Status {

   PENDING("PENDING"),
   VALIDATED("VALIDATED"),
   CANCELLED("CANCELLED");

   private final String value;

   private Status(String value) {
      this.value = value;
   }

   @Override
   public String toString() {
      return value;
   }
}
