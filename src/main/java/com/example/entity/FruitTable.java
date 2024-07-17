package com.example.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
public class FruitTable implements Serializable {

  private static long serialVersionUID = 1L;

  @Id
  private Integer id;

  private String fruitName;

  private Double price;

  private String img;

  private Integer category;

  private Integer inventory;

  private LocalDateTime time;
}
