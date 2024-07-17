package com.example.repository;

import com.example.entity.FruitSell;
import com.example.entity.MeetingTable;
import com.example.entity.MessageTable;
import com.example.query.MessageQuery;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageRepository extends R2dbcRepository<MessageTable, Integer> {

  @Query("select * from message_table " +
    "where (:#{#messageQuery.messageContent} is null or message_content like concat('%', :#{#messageQuery.messageContent}, '%')) " +
    "limit :start, :pageSize")
  Flux<MessageTable> findAllBy(Integer start, Integer pageSize, MessageQuery messageQuery);

  @Query("select * from message_table order by message_time desc limit 5")
  Flux<MessageTable> findMessageOrder();
}
