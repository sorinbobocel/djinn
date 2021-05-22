package com.butlersuite.djinn.dto;

import com.butlersuite.djinn.model.CustomerDetails;
import lombok.Data;

@Data
public class CustomerDTO {

   private Long customerId;

   private String companyName;

   private String customerEmail;

   private String customerPassword;

   private CustomerDetails customerDetails;
}
