package com.example.library.app.controller;

import com.example.library.app.dto.ResponseResult;
import com.example.library.app.handler.ResponseResults;
import com.example.library.app.security.SecurityUtils;
import com.example.library.core.dto.BookDetailDto;
import com.example.library.service.service.BookFavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookFavoriteController {

    private final BookFavoriteService favoriteService;

    public BookFavoriteController(BookFavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/{id}/favorite/add")
    public ResponseEntity<ResponseResult<Void>> add(@PathVariable("id") Integer bookId) {
        Integer uid = SecurityUtils.getCurrentUid();
        String name = SecurityUtils.getCurrentName();
        favoriteService.add(uid, bookId, name);
        return ResponseEntity.ok(ResponseResults.ok("图书收藏成功", null));
    }

    @PostMapping("/{id}/favorite/remove")
    public ResponseEntity<ResponseResult<Void>> remove(@PathVariable("id") Integer bookId) {
        Integer uid = SecurityUtils.getCurrentUid();
        String name = SecurityUtils.getCurrentName();
        favoriteService.remove(uid, bookId, name);
        return ResponseEntity.ok(ResponseResults.ok("图书已取消收藏", null));
    }

    @GetMapping("/favorites")
    public ResponseEntity<ResponseResult<List<BookDetailDto>>> list() {
        Integer uid = SecurityUtils.getCurrentUid();
        List<BookDetailDto> data = favoriteService.list(uid);
        return ResponseEntity.ok(ResponseResults.ok(data));
    }
}
