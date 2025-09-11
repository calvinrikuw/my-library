package com.example.library.core.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FindBookDto {
    private Integer id;
    private String title;
    private String author;
    private String isbn;
    private String categories;
    private String summary;
    private String coverUrl;
}