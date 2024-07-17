package com.example.query;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MessageQuery extends BaseQuery{

  private String messageContent;

}
