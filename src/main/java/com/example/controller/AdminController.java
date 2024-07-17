package com.example.controller;

import com.example.entity.AdminTable;
import com.example.query.AdminQuery;
import com.example.service.IAdminService;
import com.example.vo.AdminVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/admin")
public class AdminController {

  @Resource
  private IAdminService iAdminService;

  @Autowired
  public RabbitTemplate rabbitTemplate;

  @PostMapping("/page")
  public Mono<Page<AdminVo>> queryAdmin(@RequestBody AdminQuery adminQuery) {
    return iAdminService.queryAdmin(adminQuery);
  }

  @GetMapping("/get/{id}")
  public Mono<AdminTable> getById(@PathVariable Integer id) {
    return iAdminService.getById(id);
  }

  @PostMapping("/add")
  public Mono<ResponseEntity<Void>> addAdmin(@RequestBody AdminTable adminTable) {
    return iAdminService.addAdmin(adminTable);
  }

  @DeleteMapping("/delete/{id}")
  public Mono<ResponseEntity<Void>> deleteAdmin(@PathVariable Integer id) {
    return iAdminService.deleteAdmin(id);
  }

  @PutMapping("/update")
  public Mono<ResponseEntity<Void>> updateUser(@RequestBody AdminTable adminTable) {
    return iAdminService.updateUser(adminTable);
  }
}
