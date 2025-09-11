package com.example.library.app.dto;

import jakarta.validation.constraints.NotBlank;

public class BookUploadRequest {

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "author is required")
    private String author;

    @NotBlank(message = "isbn is required")
    private String isbn;

    @NotBlank(message = "categories is required")
    private String categories;

    @NotBlank(message = "summary is required")
    private String summary;

    private Integer ownerUserId;
}
