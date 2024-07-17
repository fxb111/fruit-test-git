package com.example.service.impl;

import com.example.entity.FruitTable;
import com.example.query.FruitQuery;
import com.example.repository.FruitRepository;
import com.example.service.IFruitService;
import com.example.vo.FruitVo;
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

@Service
public class FruitServiceImpl implements IFruitService {

  @Resource
  private FruitRepository fruitRepository;

  @Resource
  private R2dbcEntityTemplate r2dbcEntityTemplate;

  @Override
  public Mono<Page<FruitVo>> queryFruit(FruitQuery fruitQuery) {
    if (fruitQuery.getCategory() == 1) {
      return fruitRepository
        .findAllByOne((fruitQuery.getCurrentPage() - 1) * fruitQuery.getPageSize(), fruitQuery.getPageSize(), fruitQuery)
        .collectList()
        .map(list -> new PageImpl<>(list));
    } else {
      return fruitRepository
        .findAllBy((fruitQuery.getCurrentPage() - 1) * fruitQuery.getPageSize(), fruitQuery.getPageSize(), fruitQuery)
        .collectList()
        .map(list -> new PageImpl<>(list));
    }
  }

  @Override
  public Mono<FruitTable> getById(Integer id) {
    return fruitRepository.findById(id)
      .switchIfEmpty(Mono.error(new Exception("水果不存在")));
  }

  @Override
  public Mono<ResponseEntity<Void>> addFruit(FruitTable fruitTable) {
    return checkNameExist(fruitTable)
      .flatMap(fruitTable1 -> fruitRepository.save(fruitTable1).then(Mono.just(new ResponseEntity<>(HttpStatus.OK))));
  }

  @Override
  public Mono<ResponseEntity<Void>> deleteFruit(Integer id) {
    return fruitRepository.findById(id)
      .flatMap(fruitTable -> fruitRepository.delete(fruitTable).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
      .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.BAD_REQUEST));
  }

  @Override
  public Mono<ResponseEntity<Void>> updateFruit(FruitTable fruitTable) {
    return checkNameExist(fruitTable)
      .flatMap(fruitTable1 -> fruitRepository.save(fruitTable1).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))));
  }

  private Mono<FruitTable> checkNameExist(FruitTable fruitTable) {
    return r2dbcEntityTemplate
      .select(FruitTable.class)
      .matching(Query.query(Criteria.where("fruit_name").is(fruitTable.getFruitName())))
      .all()
      .collectList()
      .flatMap(list -> list.isEmpty() ? Mono.just(fruitTable) : Mono.error(new Exception("水果名重复")));
  }
}
