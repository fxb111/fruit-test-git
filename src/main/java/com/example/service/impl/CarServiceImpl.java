package com.example.service.impl;

import com.example.entity.CarTable;
import com.example.entity.OrderTable;
import com.example.query.CarQuery;
import com.example.query.PayRequest;
import com.example.repository.CarRepository;
import com.example.repository.UserRepository;
import com.example.service.ICarService;
import com.example.utils.R;
import com.example.vo.CarAllVo;
import com.example.vo.CarVo;
import jakarta.annotation.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements ICarService {

  @Resource
  private CarRepository carRepository;

  @Resource
  private OrderServiceImpl orderService;

  @Resource
  private UserRepository userRepository;

  @Resource
  private R2dbcEntityTemplate r2dbcEntityTemplate;

  @Override
  public Mono<List<CarVo>> getCarAllByUser(CarQuery carQuery) {
    return carRepository
      .getCarAllByUser(carQuery.getCarUser())
      .collectList()
      .map(list -> {
        ArrayList<CarVo> carList = new ArrayList<>();
        for (CarAllVo carAllVo : list) {
          CarVo carVo = new CarVo();
          carVo.setCarNum(carAllVo.getCarNum());
          carVo.setCarPrice(carAllVo.getCarNum() * carAllVo.getFruitPrice());
          carVo.setFruitName(carAllVo.getFruitName());
          carVo.setFruitPrice(carAllVo.getFruitPrice());
          carVo.setImg(carAllVo.getImg());
          carList.add(carVo);
        }
        return carList;
      });
  }

  @Override
  public Mono<ResponseEntity<Void>> addCar(CarTable carTable) {
    return checkFruitExist(carTable)
      .flatMap(carTable1 -> carRepository.save(carTable1).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))));
  }

  @Override
  public Mono<ResponseEntity<Void>> deleteCar(Integer id) {
    return carRepository.findById(id)
      .flatMap(carTable -> carRepository.delete(carTable).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
      .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }

  @Override
  public Mono<ResponseEntity<Void>> updateCar(CarTable carTable) {
    return checkFruitExist(carTable)
      .flatMap(carTable1 -> carRepository.save(carTable1).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))));
  }

  @Override
  public Mono<ResponseEntity<R>> payOrder(PayRequest payRequest) {
    return Mono.just(payRequest)
      .flatMap(request -> userRepository.findById(payRequest.getUserId())
        .map(user -> {
          OrderTable order = new OrderTable();
          long currentTimeMillis = System.currentTimeMillis();
          String orderId = currentTimeMillis + Integer.toString(payRequest.getUserId());
          order.setUserName(user.getUserName())
            .setUserPhoneNumber(user.getUserPhoneNumber())
            .setOrderFruit(payRequest.getOrderFruit())
            .setOrderPrice(payRequest.getTotalPrice())
            .setShopId(payRequest.getShopId())
            .setOrderId(orderId)
            .setOrderCreateTime(LocalDateTime.now());
          return order;
        })
        .flatMap(order ->
          orderService
          .addOrder(order)
          .map(a-> ResponseEntity.ok().body(new R(200, "成功支付订单"+payRequest.getTotalPrice(), null)))));
  }

  private Mono<CarTable> checkFruitExist(CarTable carTable) {
    return r2dbcEntityTemplate
      .select(CarTable.class)
      .matching(Query.query(Criteria.where("car_fruit").is(carTable.getCarFruit())))
      .all()
      .collectList()
      .flatMap(list -> list.isEmpty() ? Mono.just(carTable) : Mono.error(new Exception("水果在购物车中已经存在")));
  }
}
