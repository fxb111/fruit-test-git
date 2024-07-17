package com.example.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CarVo {

  private String fruitName;

  private Double fruitPrice;

  private Double CarPrice;

  private Integer carNum;

  private Integer userId;

  private String userName;

  private String img;

  private Double AllPrice;

}
