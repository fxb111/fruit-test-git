package com.example.repository;

import com.example.entity.FruitSell;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface FruitSellRepository extends R2dbcRepository<FruitSell, Integer> {

  @Query("select * from fruit_sell order by quantity desc limit 5")
  Flux<FruitSell> findSellOrder();

}
