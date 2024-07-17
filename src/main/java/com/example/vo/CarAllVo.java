package com.example.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

@Data
@Accessors(chain = true)
public class CarAllVo {

  @Id
  private Integer id;

  private Integer carFruit;

  private Integer carUser;

  private Double CarPrice;

  private Integer carNum;

  private String fruitName;

  private Double fruitPrice;

  private String img;

  private Integer category;

  private Integer inventory;

}
