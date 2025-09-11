package com.example.library.app.controller;

import com.example.library.app.dto.ResponseResult;
import com.example.library.app.handler.ResponseResults;
import com.example.library.app.security.SecurityUtils;
import com.example.library.core.dto.LoanBookDto;
import com.example.library.service.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/borrow")
    public ResponseEntity<ResponseResult<Void>> borrow(@RequestParam("bookId") Integer bookId) {
        Integer uid = SecurityUtils.getCurrentUid();
        String name = SecurityUtils.getCurrentName();
        loanService.borrow(uid, bookId, name);
        return ResponseEntity.ok(ResponseResults.ok("图书借阅成功", null));

    }

    @PostMapping("/{loanId}/return")
    public ResponseEntity<ResponseResult<Void>> returnBook(@PathVariable Integer loanId) {
        Integer uid = SecurityUtils.getCurrentUid();
        String name = SecurityUtils.getCurrentName();
        loanService.returnBook(uid, loanId, name);
        return ResponseEntity.ok(ResponseResults.ok("图书归还成功", null));
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseResult<List<LoanBookDto>>> myLoanBooks() {
        Integer uid = SecurityUtils.getCurrentUid();
        return ResponseEntity.ok(ResponseResults.ok(loanService.findMyLoanBooks(uid)));
    }
}
