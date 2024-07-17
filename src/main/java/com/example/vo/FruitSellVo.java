package com.example.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FruitSellVo {

  private Integer id;

  private Integer fruitId;

  private String fruitName;

  private Integer quantity;

  private Double totalPrice;

}
