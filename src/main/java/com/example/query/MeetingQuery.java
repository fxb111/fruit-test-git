package com.example.query;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MeetingQuery extends BaseQuery{

  private String host;

}
