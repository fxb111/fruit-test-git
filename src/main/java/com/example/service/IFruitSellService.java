package com.example.service;

import com.example.entity.FruitSell;
import com.example.vo.FruitSellVo;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IFruitSellService {
  Mono<List<FruitSell>> getFruitSell();

}
