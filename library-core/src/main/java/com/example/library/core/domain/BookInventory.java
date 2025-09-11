package com.example.library.core.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookInventory {
    private Integer id;
    private Integer bookId;
    private Integer ownerUserId;
    private String status;
    private String createdBy;
    private LocalDateTime createdTime;
    private String updatedBy;
    private LocalDateTime updatedTime;
    private Integer isDeleted;
}
