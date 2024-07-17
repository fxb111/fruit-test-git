package com.example.controller;

import com.example.entity.MeetingTable;
import com.example.query.MeetingQuery;
import com.example.service.IMeetingService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/meeting")
public class MeetingController {

  @Resource
  private IMeetingService iMeetingService;

  @GetMapping("/page")
  public Mono<Page<MeetingTable>> queryMeeting(@RequestBody MeetingQuery meetingQuery) {
    return iMeetingService.queryMeeting(meetingQuery);
  }

  @GetMapping("/get/{id}")
  public Mono<MeetingTable> getById(@PathVariable Integer id) {
    return iMeetingService.getById(id);
  }

  @PostMapping("/add")
  public Mono<ResponseEntity<Void>> addMeeting(@RequestBody MeetingTable meetingTable) {
    return iMeetingService.addMeeting(meetingTable);
  }

  @DeleteMapping("/delete/{id}")
  public Mono<ResponseEntity<Void>> deleteMeeting(@PathVariable Integer id) {
    return iMeetingService.deleteMeeting(id);
  }

  @PutMapping("/update")
  public Mono<ResponseEntity<Void>> updateMeeting(@RequestBody MeetingTable meetingTable) {
    return iMeetingService.updateMeeting(meetingTable);
  }

  @GetMapping("/exportExcel/{id}")
  @ResponseBody
  public Mono<Void> exportExcel(@PathVariable Integer id, ServerWebExchange exchange) {
    return iMeetingService.exportExcel(id, exchange);
  }
}
