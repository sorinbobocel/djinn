package com.butlersuite.djinn.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@Getter
@Setter
@ToString
public class OrderDetail {

   @Embedded
   private ProductInfo productInfo;

   private int quantity;

   public OrderDetail() {
   }

   public OrderDetail(ProductInfo productInfo, int quantity) {
      this.productInfo = productInfo;
      this.quantity = quantity;
   }
}
