package com.example.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.entity.MeetingTable;
import com.example.query.MeetingExcel;
import com.example.query.MeetingQuery;
import com.example.repository.MeetingRepository;
import com.example.service.IMeetingService;
import jakarta.annotation.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Service
public class MeetingServiceImpl implements IMeetingService {

  @Resource
  private MeetingRepository meetingRepository;

  @Override
  public Mono<Page<MeetingTable>> queryMeeting(MeetingQuery meetingQuery) {
    if (meetingQuery.getHost() != null && meetingQuery.getHost().isEmpty()) {
      return meetingRepository
        .findAllBy((meetingQuery.getCurrentPage() - 1) * meetingQuery.getPageSize(), meetingQuery.getPageSize(), meetingQuery.getHost())
        .collectList()
        .map(list -> new PageImpl<>(list));
    } else {
      return meetingRepository
        .findAllByPage((meetingQuery.getCurrentPage() - 1) * meetingQuery.getPageSize(), meetingQuery.getPageSize())
        .collectList()
        .map(list -> new PageImpl<>(list));
    }
  }

  @Override
  public Mono<MeetingTable> getById(Integer id) {
    return meetingRepository.findById(id);
  }

  @Override
  public Mono<ResponseEntity<Void>> addMeeting(MeetingTable meetingTable) {
    return checkNameExist(meetingTable)
      .flatMap(meetingTable1 -> meetingRepository.save(meetingTable1).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))));
  }

  @Override
  public Mono<ResponseEntity<Void>> deleteMeeting(Integer id) {
    return meetingRepository.findById(id)
      .flatMap(meetingTable -> meetingRepository.delete(meetingTable).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
      .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }

  @Override
  public Mono<ResponseEntity<Void>> updateMeeting(MeetingTable meetingTable) {
    return checkNameExist(meetingTable)
      .flatMap(meetingTable1 -> meetingRepository.save(meetingTable1).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))));
  }

  @Override
  public Mono<Void> exportExcel(Integer id, ServerWebExchange exchange) {
    ServerHttpResponse response = exchange.getResponse();
    DataBufferFactory bufferFactory = response.bufferFactory();
    String templateFilePath = "/Users/fukunxin/Java projects/ManagerSystem_WebFlux_副本/src/main/resources/templates/meetingTemplates.xlsx";

    return meetingRepository.findById(id)
      .map(meetingTable -> {
        try (ByteArrayOutputStream baoS = new ByteArrayOutputStream()) {
          ExcelWriter excelWriter = EasyExcel.write(baoS, MeetingExcel.class)
            .withTemplate(templateFilePath)
            .excelType(ExcelTypeEnum.XLSX)
            .build();

          WriteSheet sheet = EasyExcel.writerSheet().build();
          HashMap<String, String> mapData = new HashMap<>();
          mapData.put("meetingName", meetingTable.getMeetingName());
          mapData.put("host", meetingTable.getHost());
          mapData.put("meetingPerson", meetingTable.getMeetingPerson());
          mapData.put("meetingStart", meetingTable.getMeetingStart().toString().replace("T", " "));
          mapData.put("meetingEnd", meetingTable.getMeetingEnd().toString().replace("T", " "));
          mapData.put("meetingContent", meetingTable.getMeetingContent());

          excelWriter.fill(mapData, sheet);
          excelWriter.finish();

          byte[] bytes = baoS.toByteArray();
          String fileName = URLEncoder.encode("会议记录.xlsx", StandardCharsets.UTF_8.name());
          response.getHeaders().add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
          response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);

          DataBuffer buffer = bufferFactory.wrap(bytes);
          return Flux.just(buffer);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      })
      .flatMap(bufferFlux -> response.writeWith(bufferFlux))
      .onErrorResume(e ->
        Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to export meeting data", e)));
  }

  private Mono<MeetingTable> checkNameExist(MeetingTable meetingTable) {
    MeetingTable query = new MeetingTable();
    query.setMeetingName(meetingTable.getMeetingName());
    Example<MeetingTable> example = Example.of(query);
    return meetingRepository.findAll(example).collectList()
      .flatMap(list -> {
        if (list.isEmpty()) {
          return Mono.just(meetingTable);
        } else {
          return Mono.error(new Exception("会议名重复"));
        }
      });
  }
}
