package com.example.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class CommentTable implements Serializable {

  private static long serialVersionUID = 1L;

  @Id
  private Integer id;

  private String commentContent;

  private String commentImg;

  private LocalDateTime commentTime;

}