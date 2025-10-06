package com.example.library.app.controller;

import com.example.library.app.dto.ResponseResult;
import com.example.library.app.handler.ResponseResults;
import com.example.library.app.security.SecurityUtils;
import com.example.library.core.dto.LoanBookDto;
import com.example.library.core.exception.BusinessException;
import com.example.library.service.service.LoanService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
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

    @GetMapping(value = "/export", produces = "text/csv;charset=UTF-8")
    public void exportMyLoans(HttpServletResponse resp) {
        Integer uid = SecurityUtils.getCurrentUid();
        String filename = "my_loans_" + LocalDate.now() + ".csv";
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("text/csv; charset=UTF-8");
        resp.setHeader("Content-Disposition", "attachment; filename=" + filename);

        try (Writer writer = resp.getWriter()) {
            // 写入 UTF-8 BOM，避免 Excel 乱码
            writer.write('\uFEFF');
            loanService.exportMyLoansCsv(uid, writer);
        } catch (IOException ex) {
            throw new BusinessException("EXPORT_FAIL", "导出文件失败");
        }
    }
}
