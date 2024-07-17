package com.example.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class UserTable implements Serializable {

  private static long serialVersionUID = 1L;

  @Id
  private Integer id;

  private Integer userId;

  private String userName;

  private String userPassword;

  private String userImg;

  private String userRealName;

  private String userPhoneNumber;

  private String userAddress;

  private LocalDateTime userCreateTime;

}
