package com.example.query;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseQuery {

  //当前页码
  private Integer currentPage;

  //每页条数
  private Integer pageSize;

}
