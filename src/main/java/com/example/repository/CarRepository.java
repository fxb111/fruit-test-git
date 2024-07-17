package com.example.repository;

import com.example.entity.AdminTable;
import com.example.entity.CarTable;
import com.example.vo.CarAllVo;
import com.example.vo.CarVo;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface CarRepository extends R2dbcRepository<CarTable, Integer> {
  @Query("select * from car_table a left join user_table b on a.car_user = b.id left join fruit_table c on a.car_fruit = c.id")
  Flux<CarAllVo> getCarAllByUser(Integer carUser);

}
