package com.example.query;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FruitQuery extends BaseQuery{

  private String fruitName;

  private Integer category;

}
