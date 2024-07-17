package com.example.service;

import com.example.entity.OrderTable;
import com.example.query.OrderQuery;
import com.example.vo.OrderVo;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface IOrderService {
  Mono<Page<OrderVo>> queryOrder(OrderQuery orderQuery);

  Mono<OrderTable> getOrderById(Integer id);

  Mono<ResponseEntity<Void>> addOrder(OrderTable orderTable);

  Mono<ResponseEntity<Void>> deleteOrder(Integer id);

  Mono<ResponseEntity<Void>> updateOrder(OrderTable orderTable);

}
