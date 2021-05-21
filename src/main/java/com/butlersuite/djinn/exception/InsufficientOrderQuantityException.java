package com.butlersuite.djinn.exception;

public class InsufficientOrderQuantityException extends RuntimeException {

   public InsufficientOrderQuantityException(String message) {
      super(message);
   }
}
