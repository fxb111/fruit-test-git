package com.example.controller;

import com.example.entity.UserTable;
import com.example.query.UserQuery;
import com.example.service.IUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/user")
public class UserController {

  @Resource
  private IUserService iUserService;

  @PostMapping("/page")
  public Mono<Page<UserTable>> queryUser(@RequestBody UserQuery userQuery) {
    return iUserService.queryUser(userQuery);
  }

  @GetMapping("/get/{id}")
  public Mono<UserTable> getById(@PathVariable Integer id) {
    return iUserService.getById(id);
  }

  @PostMapping("/add")
  public Mono<ResponseEntity<Void>> addUser(@RequestBody UserTable userTable) {
    return iUserService.addUser(userTable);
  }

  @DeleteMapping("/delete/{id}")
  public Mono<ResponseEntity<Void>> deleteUser(@PathVariable Integer id) {
    return iUserService.deleteUser(id);
  }

  @PutMapping("/update")
  public Mono<ResponseEntity<Void>> updateUser(@RequestBody UserTable userTable) {
    return iUserService.updateUser(userTable);
  }
}
