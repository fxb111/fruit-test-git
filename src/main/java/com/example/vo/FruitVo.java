package com.example.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class FruitVo {

  private Integer id;

  private String fruitName;

  private Double price;

  private String img;

  private Integer category;

  private String categoryName;

  private Integer inventory;

  private LocalDateTime time;

}
