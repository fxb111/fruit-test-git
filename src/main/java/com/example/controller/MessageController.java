package com.example.controller;

import com.example.entity.MeetingTable;
import com.example.entity.MessageTable;
import com.example.query.MessageQuery;
import com.example.service.IMessageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/message")
public class MessageController {

  @Resource
  private IMessageService iMessageService;

  @GetMapping("/page")
  public Mono<Page<MessageTable>> queryMessage(@RequestBody MessageQuery messageQuery) {
    return iMessageService.queryMessage(messageQuery);
  }

  @GetMapping("/get/{id}")
  public Mono<MessageTable> getById(@PathVariable Integer id) {
    return iMessageService.getById(id);
  }

  @PostMapping("/add")
  public Mono<ResponseEntity<Void>> addMessage(@RequestBody MessageTable messageTable) {
    return iMessageService.addMessage(messageTable);
  }

  @DeleteMapping("/delete/{id}")
  public Mono<ResponseEntity<Void>> deleteMessage(@PathVariable Integer id) {
    return iMessageService.deleteMessage(id);
  }

  @PostMapping("update")
  public Mono<ResponseEntity<Void>> updateMessage(@RequestBody MessageTable messageTable) {
    return iMessageService.updateMessage(messageTable);
  }
}
