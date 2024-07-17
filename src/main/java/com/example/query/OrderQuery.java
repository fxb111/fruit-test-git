package com.example.query;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderQuery extends BaseQuery{

  private String orderId;

  private Integer shopId;

}
