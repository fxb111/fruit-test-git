package com.example.controller;

import com.example.entity.CommentTable;
import com.example.entity.FruitTable;
import com.example.query.CommentQuery;
import com.example.query.FruitQuery;
import com.example.service.ICommentService;
import com.example.service.IFruitSellService;
import com.example.service.IFruitService;
import com.example.vo.FruitVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/comment")
public class CommentController {

  @Resource
  private ICommentService iCommentService;

  @PostMapping("/page")
  public Mono<Page<CommentTable>> queryComment(@RequestBody CommentQuery commentQuery) {
    return iCommentService.queryComment(commentQuery);
  }

  @GetMapping("/get/{id}")
  public Mono<CommentTable> getById(@PathVariable Integer id) {
    return iCommentService.getById(id);
  }

  @PostMapping("/add")
  public Mono<ResponseEntity<Void>> addComment(@RequestBody CommentTable commentTable) {
    return iCommentService.addComment(commentTable);
  }

  @DeleteMapping("/delete/{id}")
  public Mono<ResponseEntity<Void>> deleteComment(@PathVariable Integer id) {
    return iCommentService.deleteComment(id);
  }

  @PutMapping("/update")
  public Mono<ResponseEntity<Void>> updateComment(@RequestBody CommentTable commentTable) {
    return iCommentService.updateComment(commentTable);
  }
}
