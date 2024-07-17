package com.example.service.impl;

import com.example.entity.OrderTable;
import com.example.query.OrderQuery;
import com.example.repository.OrderRepository;
import com.example.service.IOrderService;
import com.example.vo.OrderVo;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OrderServiceImpl implements IOrderService {

  @Resource
  private OrderRepository orderRepository;

  @Resource
  private R2dbcEntityTemplate r2dbcEntityTemplate;

  @Override
  public Mono<Page<OrderVo>> queryOrder(OrderQuery orderQuery) {
    if (orderQuery.getShopId() == 1) {
      return orderRepository
        .findAllByOne((orderQuery.getCurrentPage() - 1) * orderQuery.getPageSize(), orderQuery.getPageSize(), orderQuery)
        .collectList()
        .map(list -> new PageImpl<>(list));
    } else {
      return orderRepository
        .findAllBy((orderQuery.getCurrentPage() - 1) * orderQuery.getPageSize(), orderQuery.getPageSize(), orderQuery)
        .collectList()
        .map(list -> new PageImpl<>(list));
    }
  }

  @Override
  public Mono<OrderTable> getOrderById(Integer id) {
    return orderRepository.findById(id)
      .switchIfEmpty(Mono.error(new Exception("没有找到这个订单")));
  }

  @Override
  public Mono<ResponseEntity<Void>> addOrder(OrderTable orderTable) {
    return checkOrderIdExist(orderTable)
      .flatMap(orderTable1 -> orderRepository.save(orderTable1).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))));
  }

  @Override
  public Mono<ResponseEntity<Void>> deleteOrder(Integer id) {
    return orderRepository.findById(id)
      .flatMap(orderTable -> orderRepository.delete(orderTable).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
      .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }

  @Override
  public Mono<ResponseEntity<Void>> updateOrder(OrderTable orderTable) {
    return checkOrderIdExist(orderTable)
      .flatMap(orderTable1 -> orderRepository.save(orderTable1).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))));
  }

  private Mono<OrderTable> checkOrderIdExist(OrderTable orderTable) {
    return r2dbcEntityTemplate
      .select(OrderTable.class)
      .matching(Query.query(Criteria.where("order_id").is(orderTable.getOrderId())))
      .all()
      .collectList()
      .flatMap(list -> list.isEmpty() ? Mono.just(orderTable) : Mono.error(new Exception("该订单号已经被占用")));
  }
}
