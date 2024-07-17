package com.example.service;

import com.example.entity.CarTable;
import com.example.query.CarQuery;
import com.example.query.PayRequest;
import com.example.utils.R;
import com.example.vo.CarVo;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICarService {
  Mono<List<CarVo>> getCarAllByUser(CarQuery carQuery);

  Mono<ResponseEntity<Void>> addCar(CarTable carTable);

  Mono<ResponseEntity<Void>> deleteCar(Integer id);

  Mono<ResponseEntity<Void>> updateCar(CarTable carTable);

  Mono<ResponseEntity<R>> payOrder(PayRequest payRequest);

}
