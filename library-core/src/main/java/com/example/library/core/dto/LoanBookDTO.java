package com.example.library.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class LoanBookDTO {
    private int loanId;
    private int bookId;
    private int inventoryId;
    private String title;
    private String author;
    private String coverUrl;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private int userId;
    private String createdBy;

    public LoanBookDTO(int loanId, int userId, int inventoryId, LocalDate loanDate, String createdBy) {
        this.loanId = loanId;
        this.userId = userId;
        this.inventoryId = inventoryId;
        this.loanDate = loanDate;
        this.createdBy = createdBy;
    }

    public LoanBookDTO(int loanId, int bookId, int inventoryId, String title, String author, String coverUrl, LocalDate loanDate, LocalDate returnDate) {
        this.loanId = loanId;
        this.bookId = bookId;
        this.inventoryId = inventoryId;
        this.title = title;
        this.author = author;
        this.coverUrl = coverUrl;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }
}
