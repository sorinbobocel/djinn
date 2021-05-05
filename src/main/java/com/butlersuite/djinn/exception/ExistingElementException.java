package com.butlersuite.djinn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ExistingElementException extends RuntimeException {

   public ExistingElementException(String message) {
      super(message);
   }
}
