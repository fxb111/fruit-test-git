package com.example.repository;

import com.example.entity.OrderTable;
import com.example.query.OrderQuery;
import com.example.vo.OrderVo;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderRepository extends R2dbcRepository<OrderTable, Integer> {

  @Query("select * from order_table a left join shop_table b on a.shop_id = b.shop_id " +
    "where (a.shop_id = :#{#orderQuery.shopId}) " +
    "AND (:#{#orderQuery.orderId}) is not null and a.order_id like concat('%', :#{#orderQuery.orderId}, '%') " +
    "limit :start, :pageSize")
  Flux<OrderVo> findAllBy(Integer start, Integer pageSize, OrderQuery orderQuery);

  @Query("select * from order_table a left join shop_table b on a.shop_id = b.shop_id " +
    "where (:#{#orderQuery.orderId}) is not null and a.order_id like concat('%', :#{#orderQuery.orderId}, '%') " +
    "limit :start, :pageSize")
  Flux<OrderVo> findAllByOne(Integer start, Integer pageSize, OrderQuery orderQuery);

}
