package com.example.library.service.service;

import com.example.library.core.dto.BookDetailDto;
import com.example.library.core.dto.FindBookDto;
import com.example.library.core.dto.PageResult;
import com.example.library.core.exception.BusinessException;
import com.example.library.service.repository.BookMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class BookService {

    private final BookMapper bookMapper;

    public BookService(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public PageResult<FindBookDto> search(String category, String query, int page, int size) {
        // 是否按分类筛选
        boolean filterCategory = (category != null && !"all".equalsIgnoreCase(category));
        int pageNum = page <= 0 ? 1 : page;
        int limit = size <= 0 ? 10 : Math.min(size, 20);
        int offset = (pageNum - 1) * limit;

        List<String> keywords = null;
        if (query != null && !query.isBlank()) {
            // 按空白字符进行分割出关键字
            keywords = Arrays.stream(query.trim().split("\\s+"))
                    .filter(s -> !s.isBlank())
                    .toList();
        }
        long total = bookMapper.countByKeywords(filterCategory ? category : null, keywords);

        List<FindBookDto> rows;
        if (total == 0) {
            rows = Collections.emptyList();
        } else {
            rows = bookMapper.find(filterCategory ? category : null, keywords, limit, offset);
        }
        return new PageResult<>(total, pageNum, limit, rows);
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
