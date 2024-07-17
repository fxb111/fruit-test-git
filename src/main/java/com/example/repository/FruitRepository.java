package com.example.repository;

import com.example.entity.FruitTable;
import com.example.query.FruitQuery;
import com.example.vo.FruitVo;
import org.springframework.data.domain.Page;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface FruitRepository extends R2dbcRepository<FruitTable, Integer> {

  @Query("select * from fruit_table a left join fruit_category b on a.category = b.category_id " +
    "where (a.category = :#{#fruitQuery.category})" +
    "AND ((:#{#fruitQuery.fruitName}) is not null and a.fruit_name like concat('%', :#{#fruitQuery.fruitName}, '%'))" +
    "limit :start, :pageSize")
  Flux<FruitVo> findAllBy(Integer start, Integer pageSize, FruitQuery fruitQuery);

  @Query("select * from fruit_table a left join fruit_category b on a.category = b.category_id " +
    "where ((:#{#fruitQuery.fruitName}) is not null and a.fruit_name like concat('%', :#{#fruitQuery.fruitName}, '%'))" +
    "limit :start, :pageSize")
  Flux<FruitVo> findAllByOne(Integer start, Integer pageSize, FruitQuery fruitQuery);

}
