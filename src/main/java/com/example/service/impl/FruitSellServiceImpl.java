package com.example.service.impl;

import com.example.entity.FruitSell;
import com.example.repository.FruitSellRepository;
import com.example.service.IFruitSellService;
import com.example.vo.FruitSellVo;
import jakarta.annotation.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class FruitSellServiceImpl implements IFruitSellService {

  @Resource
  private FruitSellRepository fruitSellRepository;

  @Resource
  private R2dbcEntityTemplate r2dbcEntityTemplate;

  @Override
  public Mono<List<FruitSell>> getFruitSell() {
    return fruitSellRepository.findSellOrder()
      .collectList()
      .map(list -> new ArrayList<>(list));
  }
}
