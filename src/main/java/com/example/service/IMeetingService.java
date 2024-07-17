package com.example.service;

import com.example.entity.MeetingTable;
import com.example.query.MeetingQuery;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface IMeetingService {
  Mono<Page<MeetingTable>> queryMeeting(MeetingQuery meetingQuery);

  Mono<ResponseEntity<Void>> addMeeting(MeetingTable meetingTable);

  Mono<ResponseEntity<Void>> deleteMeeting(Integer id);

  Mono<MeetingTable> getById(Integer id);

  Mono<ResponseEntity<Void>> updateMeeting(MeetingTable meetingTable);

  Mono<Void> exportExcel(Integer id, ServerWebExchange exchange);

}
