package com.example.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class AdminTable implements Serializable {

  private static long serialVersionUID = 1L;

  @Id
  private Integer id;

  private String username;

  private String password;

  private String realName;

  private String phoneNumber;

  private Integer shopId;

  private String img;

  private String adminCode;

  private LocalDateTime accountTime;

}
