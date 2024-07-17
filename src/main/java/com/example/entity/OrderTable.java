package com.example.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class OrderTable implements Serializable {

  private static long serialVersionUID = 1L;

  @Id
  private Integer id;

  private String orderId;

  private Integer shopId;

  private String orderFruit;

  private Double orderPrice;

  private String userName;

  private String userPhoneNumber;

  private LocalDateTime orderCreateTime;

}
