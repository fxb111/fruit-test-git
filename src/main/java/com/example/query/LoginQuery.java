package com.example.query;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginQuery {

  private String username;

  private String password;

  private String IPRegion;

}
