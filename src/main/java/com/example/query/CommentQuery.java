package com.example.query;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CommentQuery extends BaseQuery{

  private String commentContent;

}
