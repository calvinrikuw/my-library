package com.example.library.service.service;

import com.example.library.core.dto.UploadBookDto;
import com.example.library.core.dto.UploadBookResult;
import com.example.library.core.exception.BusinessException;
import com.example.library.service.repository.BookMapper;
import com.example.library.service.storage.CoverData;
import com.example.library.service.storage.LocalFileStorage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookUploadService {

    private final BookMapper bookMapper;
    private final LocalFileStorage storage;

    public BookUploadService(BookMapper bookMapper, LocalFileStorage storage) {
        this.bookMapper = bookMapper;
        this.storage = storage;
    }

    @Transactional
    public UploadBookResult upload(String title, String author, String isbn,
                                   String categories, String summary,
                                   CoverData cover, Integer userId, String userName) {
        if (isbn == null || isbn.isBlank()) {
            throw new BusinessException("ISBN_NOT_FOUND", "书籍的ISBN为空");
        }

        Integer bookId = bookMapper.getIdByIsbn(isbn.trim());

        if (bookId != null) {
            // 相同的书籍只更新库存
            bookMapper.insertInventory(bookId, userId, userName);
            return new UploadBookResult(bookId, title, null);
        }

        String coverUrl;
        try {
            coverUrl = storage.saveCover(cover);
        } catch (Exception ex) {
            throw new BusinessException("UPLOAD_FAIL", "书籍封面保存失败");
        }

        UploadBookDto book = new UploadBookDto();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setCategories(categories);
        book.setSummary(summary);
        book.setCoverUrl(coverUrl);
        book.setCreatedBy(userName);

        int bookRes = bookMapper.insertBook(book);
        if (bookRes <= 0) {
            throw new BusinessException("DB_FAIL", "新增图书失败");
        }

        bookMapper.insertInventory(book.getId(), userId, userName);
        return new UploadBookResult(book.getId(), title, coverUrl);
    }
}

