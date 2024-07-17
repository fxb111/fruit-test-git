package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Paths;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/file")
public class FileUploadController {

  @PostMapping("/upload")
  public Mono<String> uploadFile(@RequestPart("file") FilePart filePart) {
    return filePart.transferTo(Paths.get("/Users/fukunxin/Vue projects/vue-manage-system-4.2.0/src/assets/img/" + filePart.filename()))
      .then(Mono.just("上传成功"))
      .onErrorResume(e -> {
        e.printStackTrace();
        return Mono.just("上传失败："+e.getMessage());
      });
  }
}
