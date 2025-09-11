package com.example.library.app.controller;

import com.example.library.app.dto.ResponseResult;
import com.example.library.app.handler.ResponseResults;
import com.example.library.core.dto.FindBookDto;
import com.example.library.service.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<ResponseResult<List<FindBookDto>>> list(@RequestParam(value = "q", required = false) String query) {
        return ResponseEntity.ok(ResponseResults.ok(bookService.search(query, 50)));
    }
}
