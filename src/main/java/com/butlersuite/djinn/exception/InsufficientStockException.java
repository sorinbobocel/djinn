package com.butlersuite.djinn.exception;

public class InsufficientStockException extends RuntimeException{

   public InsufficientStockException(String message) {
      super(message);
   }
}
