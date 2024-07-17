package com.example.service;

import com.example.entity.UserTable;
import com.example.query.LoginQuery;
import com.example.query.UserQuery;
import com.example.utils.R;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface IUserService {
  Mono<Page<UserTable>> queryUser(UserQuery userQuery);

  Mono<UserTable> getById(Integer id);

  Mono<ResponseEntity<Void>> addUser(UserTable userTable);

  Mono<ResponseEntity<Void>> deleteUser(Integer id);

  Mono<ResponseEntity<Void>> updateUser(UserTable userTable);

    Mono<ResponseEntity<R>> ulogin(LoginQuery loginQuery);

}
