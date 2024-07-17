package com.example.repository;

import com.example.entity.AdminTable;
import com.example.query.AdminQuery;
import com.example.vo.AdminVo;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AdminRepository extends R2dbcRepository<AdminTable, Integer> {



  @Query("select * from admin_table a left join shop_table b on a.shop_id= b.shop_id " +
    "where (a.shop_id = :#{#adminQuery.shopId}) " +
    "AND (:#{#adminQuery.realName}) is not null and a.real_name like concat('%', :#{#adminQuery.realName}, '%') " +
    "limit :start, :pageSize")
  Flux<AdminVo> findAllBy(Integer start, Integer pageSize, AdminQuery adminQuery);

  @Query("select * from admin_table a left join shop_table b on a.shop_id= b.shop_id " +
    "where (:#{#adminQuery.realName}) is not null and a.real_name like concat('%', :#{#adminQuery.realName}, '%') " +
    "limit :start, :pageSize")
  Flux<AdminVo> findAllByOne(Integer start, Integer pageSize, AdminQuery adminQuery);

}
