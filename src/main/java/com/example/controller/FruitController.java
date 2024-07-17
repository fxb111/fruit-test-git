package com.example.controller;

import com.example.entity.FruitSell;
import com.example.entity.FruitTable;
import com.example.query.FruitQuery;
import com.example.service.IFruitSellService;
import com.example.service.IFruitService;
import com.example.vo.FruitSellVo;
import com.example.vo.FruitVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/fruit")
public class FruitController {

  @Resource
  private IFruitService iFruitService;

  @Resource
  private IFruitSellService iFruitSellService;

  @PostMapping("/page")
  public Mono<Page<FruitVo>> queryFruit(@RequestBody FruitQuery fruitQuery) {
    return iFruitService.queryFruit(fruitQuery);
  }

  @GetMapping("/get/{id}")
  public Mono<FruitTable> getById(@PathVariable Integer id) {
    return iFruitService.getById(id);
  }

  @PostMapping("/add")
  public Mono<ResponseEntity<Void>> addFruit(@RequestBody FruitTable fruitTable) {
    return iFruitService.addFruit(fruitTable);
  }

  @DeleteMapping("/delete/{id}")
  public Mono<ResponseEntity<Void>> deleteFruit(@PathVariable Integer id) {
    return iFruitService.deleteFruit(id);
  }

  @PutMapping("/update")
  public Mono<ResponseEntity<Void>> updateFruit(@RequestBody FruitTable fruitTable) {
    return iFruitService.updateFruit(fruitTable);
  }


}
