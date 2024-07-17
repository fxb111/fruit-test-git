package com.example.repository;

import com.example.entity.CommentTable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface CommentRepository extends R2dbcRepository<CommentTable, Integer> {

}
