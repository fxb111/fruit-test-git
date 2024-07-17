package com.example.controller;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/send")
public class SendMessage {

  @Autowired
  public RabbitTemplate rabbitTemplate;

  @GetMapping("/directMessage")
  public String sendDirectMessage() {
    String messageId = String.valueOf(UUID.randomUUID());
    String messageData = "这是一个直连消息";
    String messageTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    Map<String, Object> map = new HashMap<>();
    map.put("messageId", messageId);
    map.put("messageData", messageData);
    map.put("messageTime", messageTime);
    rabbitTemplate.convertAndSend("DirectExchange", "directRouting", map);
    return "直连消息上传成功";
  }

  @GetMapping("/sendTopicMessage1")
  public String sendTopicMessage1() {
    String messageId = String.valueOf(UUID.randomUUID());
    String messageData = "这是一个man消息";
    String messageTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    Map<String, Object> map = new HashMap<>();
    map.put("messageId", messageId);
    map.put("messageData", messageData);
    map.put("messageTime", messageTime);
    rabbitTemplate.convertAndSend("TopicExchange", "topic.man", map);
    return "man消息上传成功";
  }

  @GetMapping("/sendTopicMessage2")
  public String sendTopicMessage2() {
    String messageId = String.valueOf(UUID.randomUUID());
    String messageData = "这是一个woman消息";
    String messageTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    Map<String, Object> map = new HashMap<>();
    map.put("messageId", messageId);
    map.put("messageData", messageData);
    map.put("messageTime", messageTime);
    rabbitTemplate.convertAndSend("TopicExchange", "topic.woman", map);
    return "woman消息上传成功";
  }

  @GetMapping("/sendFanoutMessage")
  public String sendFanoutMessage() {
    String messageId = String.valueOf(UUID.randomUUID());
    String messageData = "这是一个fanout消息";
    String messageTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    Map<String, Object> map = new HashMap<>();
    map.put("messageId", messageId);
    map.put("messageData", messageData);
    map.put("messageTime", messageTime);
    rabbitTemplate.convertAndSend("fanoutExchange", null, map);
    return "fanout消息上传成功";
  }

}
