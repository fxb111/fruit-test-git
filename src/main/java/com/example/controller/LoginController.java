package com.example.controller;

import com.example.service.IUserService;
import com.example.utils.IPUtils;
import com.example.utils.R;
import com.example.query.LoginQuery;
import com.example.service.IAdminService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/user")
public class LoginController {

  @Resource
  private IAdminService iAdminService;

  @Resource
  private IUserService iUserService;

  @PostMapping("/login")
  public Mono<ResponseEntity<R>> login(ServerWebExchange request, @RequestBody LoginQuery loginQuery) {
    loginQuery.setIPRegion(getIp(request));
    return iAdminService.login(loginQuery);
  }

  @GetMapping("/getIp")
  public String getIp(ServerWebExchange request) {
    return IPUtils.getIpRegion(IPUtils.getIpAddress(request));
  }

  @PostMapping("/ulogin")
  public Mono<ResponseEntity<R>> ulogin(@RequestBody LoginQuery loginQuery) {
    return iUserService.ulogin(loginQuery);
  }
}
