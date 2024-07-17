package com.example.service;

import com.example.entity.MeetingTable;
import com.example.entity.MessageTable;
import com.example.query.MessageQuery;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IMessageService {
  Mono<Page<MessageTable>> queryMessage(MessageQuery messageQuery);

  Mono<MessageTable> getById(Integer id);

  Mono<ResponseEntity<Void>> addMessage(MessageTable messageTable);

  Mono<ResponseEntity<Void>> deleteMessage(Integer id);

  Mono<ResponseEntity<Void>> updateMessage(MessageTable messageTable);

  Mono<List<MessageTable>> getMessage();

}
