package com.example.service.impl;


import com.example.entity.UserTable;
import com.example.query.LoginQuery;
import com.example.query.UserQuery;
import com.example.repository.UserRepository;
import com.example.service.IUserService;
import com.example.utils.R;
import jakarta.annotation.Resource;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements IUserService {

  @Resource
  private UserRepository userRepository;

  @Resource
  private R2dbcEntityTemplate r2dbcEntityTemplate;

  @Override
  public Mono<Page<UserTable>> queryUser(UserQuery userQuery) {

    Criteria criteria = Criteria.empty();

    if (userQuery.getUserName() != null && !userQuery.getUserName().isEmpty()) {
      criteria = criteria.and("user_name").like('%' + userQuery.getUserName() + '%');
    }

    return r2dbcEntityTemplate
      .select(UserTable.class)
      .matching(Query.query(criteria)
        .offset((userQuery.getCurrentPage() - 1) * userQuery.getPageSize())
        .limit(userQuery.getPageSize()))
      .all()
      .collectList()
      .map(list -> new PageImpl<>(list));
  }

  @Override
  public Mono<UserTable> getById(Integer id) {
    return userRepository.findById(id);
  }

  @Override
  public Mono<ResponseEntity<Void>> addUser(UserTable userTable) {
    if (userTable.getUserCreateTime() == null) {
      userTable.setUserCreateTime(LocalDateTime.now());
    }
    if (userTable.getUserImg() == null) {
      userTable.setUserImg("IMG_0888.jpg");
    }
    return checkNameExist(userTable)
      .flatMap(userTable1 -> userRepository.save(userTable1).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))));
  }

  @Override
  public Mono<ResponseEntity<Void>> deleteUser(Integer id) {
    return userRepository.findById(id)
      .flatMap(userTable -> userRepository.delete(userTable).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
      .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }

  @Override
  public Mono<ResponseEntity<Void>> updateUser(UserTable userTable) {
    return checkNameExist(userTable)
      .flatMap(userTable1 -> userRepository.save(userTable1).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))));
  }

  @Override
  public Mono<ResponseEntity<R>> ulogin(LoginQuery loginQuery) {
    return r2dbcEntityTemplate
      .select(UserTable.class)
      .matching(Query.query(Criteria.where("user_name").is(loginQuery.getUsername()).and("user_password").is(loginQuery.getPassword())))
      .all()
      .collectList()
      .map(list -> {
        if (list.isEmpty()) {
          return ResponseEntity.ok().body(new R(201, "登陆失败", list));
        } else {
          return ResponseEntity.ok().body(new R(200, "成功", list));
        }
      });
  }

  private Mono<UserTable> checkNameExist(UserTable userTable) {
    UserTable query = new UserTable();
    query.setUserName(userTable.getUserName());
    Example<UserTable> example = Example.of(query);
    return userRepository.findAll(example).collectList()
      .flatMap(list -> {
        if (list.isEmpty()) {
          return Mono.just(userTable);
        } else {
          return Mono.error(new Exception("用户名重复"));
        }
      });
  }
}
