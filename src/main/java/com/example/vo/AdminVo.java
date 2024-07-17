package com.example.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
@Data
@Accessors(chain = true)
public class AdminVo {

  private Integer id;

  private String username;

  private String password;

  private String realName;

  private String phoneNumber;

  private Integer shopId;

  private String shopName;

  private String address;

  private String img;

  private String adminCode;

  private LocalDateTime accountTime;

}
