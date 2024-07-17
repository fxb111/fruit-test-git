package com.example.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class OrderVo {

  private Integer id;

  private String orderId;

  private Integer shopId;

  private String shopName;

  private String orderFruit;

  private Double orderPrice;

  private String userName;

  private String userPhoneNumber;

  private LocalDateTime orderCreateTime;

}
