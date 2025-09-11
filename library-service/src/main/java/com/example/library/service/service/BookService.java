package com.example.library.service.service;

import com.example.library.core.dto.FindBookDto;
import com.example.library.service.repository.BookMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BookService {

    private final BookMapper bookMapper;

    public BookService(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public List<FindBookDto> search(String query, int limit) {
        List<String> keywords = null;
        if (query != null && !query.isBlank()) {
            // 按空白字符进行分割出关键字
            keywords = Arrays.stream(query.trim().split("\\s+"))
                    .filter(s -> !s.isBlank())
                    .toList();
        }
        return bookMapper.find(keywords, limit);
    }
}
