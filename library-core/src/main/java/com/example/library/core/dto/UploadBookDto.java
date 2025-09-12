package com.example.library.core.dto;

import lombok.Data;

@Data
public class UploadBookDto {
    private Integer id;
    private String title;
    private String author;
    private String isbn;
    private String categories;
    private String summary;
    private String coverUrl;
    private String createdBy;
}
