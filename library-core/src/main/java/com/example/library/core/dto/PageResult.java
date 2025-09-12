package com.example.library.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class PageResult<T> {
    private long total;
    private int page;
    private int size;
    private List<T> items;
}