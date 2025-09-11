package com.example.library.core.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Book {
    private Integer id;
    private String title;
    private String author;
    private String isbn;
    private String categories;
    private String summary;
    private String coverUrl;
    private Boolean isDeleted;
}
