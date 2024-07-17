package com.example.repository;

import com.example.entity.MeetingTable;
import org.springframework.data.domain.Page;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MeetingRepository extends R2dbcRepository<MeetingTable, Integer> {
  @Query("select * from meeting_table where host like concat('%', :host, '%') limit :start, :pageSize")
  Flux<MeetingTable> findAllBy(Integer start, Integer pageSize, String host);

  @Query("select * from meeting_table limit :start, :pageSize")
  Flux<MeetingTable> findAllByPage(Integer start, Integer pageSize);

}
