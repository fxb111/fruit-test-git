package com.example.controller;

import com.example.entity.FruitSell;
import com.example.entity.MessageTable;
import com.example.service.IFruitSellService;
import com.example.service.IFruitService;
import com.example.service.IMessageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/system")
public class SystemController {

  @Resource
  private IMessageService iMessageService;

  @Resource
  private IFruitService iFruitService;

  @Resource
  private IFruitSellService iFruitSellService;


  @GetMapping("/getSell")
  public Mono<List<FruitSell>> getFruitSell() {
    return iFruitSellService.getFruitSell();
  }

  @GetMapping ("/getMessage")
  Mono<List<MessageTable>> getMessage() {
    return iMessageService.getMessage();
  }
}
