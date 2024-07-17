package com.example.query;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PayRequest {

  private String orderFruit;

  private Integer userId;

  private Integer shopId;

  private double totalPrice;

}
