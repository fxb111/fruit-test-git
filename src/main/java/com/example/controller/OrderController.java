package com.example.controller;

import com.example.entity.OrderTable;
import com.example.query.OrderQuery;
import com.example.service.IOrderService;
import com.example.vo.OrderVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/order")
public class OrderController {

  @Resource
  private IOrderService iOrderService;

  @PostMapping("/page")
  public Mono<Page<OrderVo>> queryOrder(@RequestBody OrderQuery orderQuery) {
    return iOrderService.queryOrder(orderQuery);
  }

  @GetMapping("/get/{id}")
  public Mono<OrderTable> getOrderById(@PathVariable Integer id) {
    return iOrderService.getOrderById(id);
  }

  @PostMapping("/add")
  public Mono<ResponseEntity<Void>> addOrder(@RequestBody OrderTable orderTable) {
    return iOrderService.addOrder(orderTable);
  }

  @DeleteMapping("/delete/{id}")
  public Mono<ResponseEntity<Void>> deleteOrder(@PathVariable Integer id) {
    return iOrderService.deleteOrder(id);
  }

  @PutMapping("/update")
  public Mono<ResponseEntity<Void>> updateOrder(@RequestBody OrderTable orderTable) {
    return iOrderService.updateOrder(orderTable);
  }
}
