package com.example.service.impl;

import com.example.entity.MeetingTable;
import com.example.entity.MessageTable;
import com.example.query.MessageQuery;
import com.example.repository.MessageRepository;
import com.example.service.IMessageService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements IMessageService {

  @Resource
  private MessageRepository messageRepository;

  @Resource
  private R2dbcEntityTemplate r2dbcEntityTemplate;

  @Override
  public Mono<Page<MessageTable>> queryMessage(MessageQuery messageQuery) {
    return messageRepository.findAllBy((messageQuery.getCurrentPage() - 1) * messageQuery.getPageSize(), messageQuery.getPageSize(), messageQuery)
      .collectList()
      .map(list -> new PageImpl<>(list));
  }

  @Override
  public Mono<MessageTable> getById(Integer id) {
    return messageRepository.findById(id)
      .switchIfEmpty(Mono.error(new Exception("消息不存在")));
  }

  @Override
  public Mono<ResponseEntity<Void>> addMessage(MessageTable messageTable) {
    return messageRepository.save(messageTable).then(Mono.just(new ResponseEntity<>(HttpStatus.OK)));
  }

  @Override
  public Mono<ResponseEntity<Void>> deleteMessage(Integer id) {
    return messageRepository.findById(id)
      .flatMap(messageTable -> messageRepository.delete(messageTable).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
      .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @Override
  public Mono<ResponseEntity<Void>> updateMessage(MessageTable messageTable) {
    return messageRepository.findById(messageTable.getId())
      .flatMap(message -> messageRepository.save(messageTable).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
      .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }

  @Override
  public Mono<List<MessageTable>> getMessage() {
    return messageRepository.findMessageOrder()
      .collectList()
      .map(list -> new ArrayList<>(list));
  }
}

