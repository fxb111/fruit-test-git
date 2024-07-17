package com.example.controller;

import com.example.entity.CarTable;
import com.example.entity.FruitTable;
import com.example.query.AdminQuery;
import com.example.query.CarQuery;
import com.example.query.PayRequest;
import com.example.service.ICarService;
import com.example.utils.R;
import com.example.vo.AdminVo;
import com.example.vo.CarVo;
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
@RequestMapping("/car")
public class CarController {

  @Resource
  private ICarService iCarService;

  @PostMapping("/page")
  public Mono<List<CarVo>> getCarAllByUser(@RequestBody CarQuery carQuery) {
    return iCarService.getCarAllByUser(carQuery);
  }

  @PostMapping("/add")
  public Mono<ResponseEntity<Void>> addCar(@RequestBody CarTable carTable) {
    return iCarService.addCar(carTable);
  }

  @DeleteMapping("/delete/{id}")
  public Mono<ResponseEntity<Void>> deleteCar(@PathVariable Integer id) {
    return iCarService.deleteCar(id);
  }

  @PutMapping("/update")
  public Mono<ResponseEntity<Void>> updateCar(@RequestBody CarTable carTable) {
    return iCarService.updateCar(carTable);
  }

  @PostMapping("/pay")
  public Mono<ResponseEntity<R>> payOrder(@RequestBody PayRequest payRequest) {
    return iCarService.payOrder(payRequest);
  }
}
