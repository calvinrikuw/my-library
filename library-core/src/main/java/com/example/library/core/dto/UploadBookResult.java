package com.example.library.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UploadBookResult {
    private Integer bookId;
    private String title;
    private String coverUrl;
}
