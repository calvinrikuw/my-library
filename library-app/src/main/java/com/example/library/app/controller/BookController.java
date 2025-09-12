package com.example.library.app.controller;

import com.example.library.app.dto.ResponseResult;
import com.example.library.app.dto.UploadBookRequest;
import com.example.library.app.handler.ResponseResults;
import com.example.library.app.security.SecurityUtils;
import com.example.library.core.dto.BookDetailDto;
import com.example.library.core.dto.FindBookDto;
import com.example.library.core.dto.PageResult;
import com.example.library.core.dto.UploadBookResult;
import com.example.library.service.service.BookService;
import com.example.library.service.service.BookUploadService;
import com.example.library.service.storage.CoverData;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    private final BookUploadService bookUploadService;

    public BookController(BookService bookService, BookUploadService bookUploadService) {
        this.bookService = bookService;
        this.bookUploadService = bookUploadService;
    }

    @GetMapping
    public ResponseEntity<ResponseResult<PageResult<FindBookDto>>> list(@RequestParam(value = "q", required = false) String query,
                                                                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                                        @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
        return ResponseEntity.ok(ResponseResults.ok(bookService.search(query, page, size)));
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseResult<UploadBookResult>> upload(
            @Valid @ModelAttribute UploadBookRequest req) throws Exception {

        Integer uid = SecurityUtils.getCurrentUid();
        String name = SecurityUtils.getCurrentName();

        CoverData cover = null;
        if (req.getCover() != null && !req.getCover().isEmpty()) {
            cover = new CoverData(req.getCover().getBytes(), req.getCover().getOriginalFilename());
        }

        UploadBookResult res = bookUploadService.upload(req.getTitle(), req.getAuthor(),
                req.getIsbn(), req.getCategories(), req.getSummary(), cover, uid, name);

        return ResponseEntity.ok(ResponseResults.ok("图书上架成功", res));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseResult<BookDetailDto>> detail(@PathVariable("id") Integer id) {
        BookDetailDto dto = bookService.getBookDetail(id);
        return ResponseEntity.ok(ResponseResults.ok(dto));
    }

}
