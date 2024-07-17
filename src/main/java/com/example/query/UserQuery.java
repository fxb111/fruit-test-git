package com.example.query;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserQuery extends BaseQuery{

  private String userName;

}
