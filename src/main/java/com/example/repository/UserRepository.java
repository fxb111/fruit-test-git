package com.example.repository;

import com.example.entity.UserTable;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<UserTable, Integer> {


  @Query("select * from user_table where user_name like concat('%', :userName, '%') limit :start, :pageSize")
  Flux<UserTable> findAllBy(Integer start, Integer pageSize, String userName);

  @Query("select * from user_table limit :start, :pageSize")
  Flux<UserTable> findAllByPage(Integer start, Integer pageSize);

}
