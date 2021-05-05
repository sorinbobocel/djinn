package com.butlersuite.djinn.model;

public enum ProductCategory {

   PAINE("Paine"),
   PATISERIE("Patiserie"),
   COZONAC("Cozonac"),
   COLAC("Colac");

   private final String value;

   private ProductCategory(String value) {
      this.value = value;
   }

   @Override
   public String toString() {
      return value;
   }
}
