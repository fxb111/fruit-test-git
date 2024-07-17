package com.example.service;

import com.example.entity.CommentTable;
import com.example.query.CommentQuery;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface ICommentService {
  Mono<Page<CommentTable>> queryComment(CommentQuery commentQuery);

  Mono<CommentTable> getById(Integer id);

  Mono<ResponseEntity<Void>> addComment(CommentTable commentTable);

  Mono<ResponseEntity<Void>> deleteComment(Integer id);

  Mono<ResponseEntity<Void>> updateComment(CommentTable commentTable);
}
