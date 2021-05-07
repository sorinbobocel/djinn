package com.butlersuite.djinn.dto;

import com.butlersuite.djinn.model.OrderSheet;
import lombok.Data;

import java.util.Set;

@Data
public class CustomerDTO {

   private String customerName;

   private String customerEmail;

   private String customerPassword;

   private String customerPhone;

   private Set<OrderSheet> orderSheets;
}
