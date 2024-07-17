package com.example.service.impl;

import com.example.utils.R;
import com.example.entity.AdminTable;
import com.example.query.AdminQuery;
import com.example.query.LoginQuery;
import com.example.repository.AdminRepository;
import com.example.service.IAdminService;
import com.example.vo.AdminVo;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class AdminServiceImpl implements IAdminService {

  @Resource
  private AdminRepository adminRepository;

  @Resource
  private R2dbcEntityTemplate r2dbcEntityTemplate;

  @Override
  public Mono<Page<AdminVo>> queryAdmin(AdminQuery adminQuery) {
    if (adminQuery.getShopId() == 1) {
      return adminRepository
        .findAllByOne((adminQuery.getCurrentPage() - 1) * adminQuery.getPageSize(), adminQuery.getPageSize(), adminQuery)
        .collectList()
        .map(list -> new PageImpl<>(list));
    } else {
      return adminRepository
        .findAllBy((adminQuery.getCurrentPage() - 1) * adminQuery.getPageSize(), adminQuery.getPageSize(), adminQuery)
        .collectList()
        .map(list -> new PageImpl<>(list));
    }
  }

  @Override
  public Mono<AdminTable> getById(Integer id) {
    return adminRepository.findById(id)
      .switchIfEmpty(Mono.error(new Exception("该用户不存在")));
  }

  @Override
  public Mono<ResponseEntity<Void>> addAdmin(AdminTable adminTable) {
    return checkNameExist(adminTable)
      .flatMap(adminTable1 -> adminRepository.save(adminTable1).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))));
  }

  @Override
  public Mono<ResponseEntity<Void>> deleteAdmin(Integer id) {
    return adminRepository.findById(id)
      .flatMap(adminTable -> adminRepository.delete(adminTable)
        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
      .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }

  @Override
  public Mono<ResponseEntity<Void>> updateUser(AdminTable adminTable) {
    return checkNameExist(adminTable)
      .flatMap(adminTable1 -> adminRepository.save(adminTable1).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))));
  }

  @Override
  public Mono<ResponseEntity<R>> login(LoginQuery loginQuery) {
    if (!Objects.isNull(loginQuery) && loginQuery.getIPRegion().equals("内网ip")) {
      return r2dbcEntityTemplate
        .select(AdminTable.class)
        .matching(Query.query(Criteria.where("username").is(loginQuery.getUsername()).and("password").is(loginQuery.getPassword())))
        .all()
        .collectList()
        .map(list -> {
          if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
          } else {
            return ResponseEntity.ok().body(new R(200, "成功", list));
          }
        });
    } else {
      return Mono.just(ResponseEntity.badRequest().body(new R(400, "没有获取到ip信息", null)));
    }
  }

  private Mono<AdminTable> checkNameExist(AdminTable adminTable) {
    return r2dbcEntityTemplate
      .select(AdminTable.class)
      .matching(Query.query(Criteria.where("username").is(adminTable.getUsername())))
      .all()
      .collectList()
      .flatMap(list -> list.isEmpty() ? Mono.just(adminTable) : Mono.error(new Exception("用户名已存在")));
  }
}
