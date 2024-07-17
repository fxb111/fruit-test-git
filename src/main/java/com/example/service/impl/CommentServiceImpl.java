package com.example.service.impl;

import com.example.entity.AdminTable;
import com.example.entity.CommentTable;
import com.example.entity.UserTable;
import com.example.query.CommentQuery;
import com.example.repository.AdminRepository;
import com.example.repository.CommentRepository;
import com.example.service.ICommentService;
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
public class CommentServiceImpl implements ICommentService {

  @Resource
  private CommentRepository commentRepository;

  @Resource
  private R2dbcEntityTemplate r2dbcEntityTemplate;

  @Override
  public Mono<Page<CommentTable>> queryComment(CommentQuery commentQuery) {
    Criteria criteria = Criteria.empty();

    if (commentQuery.getCommentContent() != null && !commentQuery.getCommentContent().isEmpty()) {
      criteria = criteria.and("user_name").like('%' + commentQuery.getCommentContent() + '%');
    }

    return r2dbcEntityTemplate
      .select(CommentTable.class)
      .matching(Query.query(criteria)
        .offset((commentQuery.getCurrentPage() - 1) * commentQuery.getPageSize())
        .limit(commentQuery.getPageSize()))
      .all()
      .collectList()
      .map(list -> new PageImpl<>(list));
  }

  @Override
  public Mono<CommentTable> getById(Integer id) {
    return commentRepository.findById(id)
      .switchIfEmpty(Mono.error(new Exception("该订单不存在")));
  }

  @Override
  public Mono<ResponseEntity<Void>> addComment(CommentTable commentTable) {
    return checkCommentExist(commentTable)
      .flatMap(commentTable1 -> commentRepository.save(commentTable1).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))));
  }

  @Override
  public Mono<ResponseEntity<Void>> deleteComment(Integer id) {
    return commentRepository.findById(id)
      .flatMap(commentTable -> commentRepository.delete(commentTable)
        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
      .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }

  @Override
  public Mono<ResponseEntity<Void>> updateComment(CommentTable commentTable) {
    return checkCommentExist(commentTable)
      .flatMap(commentTable1 -> commentRepository.save(commentTable1).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))));
  }

  private Mono<CommentTable> checkCommentExist(CommentTable commentTable) {
    return r2dbcEntityTemplate
      .select(CommentTable.class)
      .matching(Query.query(Criteria.where("comment_content").is(commentTable.getCommentContent())))
      .all()
      .collectList()
      .flatMap(list -> list.isEmpty() ? Mono.just(commentTable) : Mono.error(new Exception("该评论已存在")));
  }
}
