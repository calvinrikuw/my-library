package com.example.library.service.service;

import com.example.library.core.dto.BookDetailDto;
import com.example.library.core.dto.FindBookDto;
import com.example.library.core.exception.BusinessException;
import com.example.library.service.repository.BookMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public BookDetailDto getBookDetail(int bookId) {
        BookDetailDto dto = bookMapper.getBookDetail(bookId);
        if (dto == null) {
            throw new BusinessException("BOOK_NOT_FOUND", "该书籍不存在");
        }
        return dto;
    }
}
