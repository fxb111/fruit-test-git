package com.example.query;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AdminQuery extends BaseQuery{

  private String realName;

  private Integer shopId;

}
