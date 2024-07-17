package com.example.service;

import com.example.utils.R;
import com.example.entity.AdminTable;
import com.example.query.AdminQuery;
import com.example.query.LoginQuery;
import com.example.vo.AdminVo;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface IAdminService {
  Mono<Page<AdminVo>> queryAdmin(AdminQuery adminQuery);

  Mono<AdminTable> getById(Integer id);

  Mono<ResponseEntity<Void>> addAdmin(AdminTable adminTable);

  Mono<ResponseEntity<Void>> deleteAdmin(Integer id);

  Mono<ResponseEntity<Void>> updateUser(AdminTable adminTable);

  Mono<ResponseEntity<R>> login(LoginQuery loginQuery);

}
