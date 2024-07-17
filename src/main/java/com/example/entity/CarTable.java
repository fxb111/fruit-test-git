package com.example.entity;

import io.r2dbc.spi.Parameter;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CarTable implements Serializable {

  private static long serialVersionUID = 1L;

  @Id
  private Integer id;

  private Integer carFruit;

  private Integer carUser;

  private Double CarPrice;

  private Integer carNum;

}
