package com.example.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class FruitSell implements Serializable {

  private static long serialVersionUID = 1L;

  @Id
  private Integer id;

  private Integer fruitId;

  private Integer quantity;

  private Double totalPrice;

}
