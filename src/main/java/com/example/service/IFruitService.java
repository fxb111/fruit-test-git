package com.example.service;

import com.example.entity.FruitTable;
import com.example.query.FruitQuery;
import com.example.vo.FruitVo;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface IFruitService {

  Mono<Page<FruitVo>> queryFruit(FruitQuery fruitQuery);

  Mono<FruitTable> getById(Integer id);

  Mono<ResponseEntity<Void>> addFruit(FruitTable fruitTable);

  Mono<ResponseEntity<Void>> deleteFruit(Integer id);

  Mono<ResponseEntity<Void>> updateFruit(FruitTable fruitTable);

}
