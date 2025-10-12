package com.example.library.service.service;

import com.example.library.core.dto.UploadBookDto;
import com.example.library.core.dto.UploadBookResult;
import com.example.library.core.exception.BusinessException;
import com.example.library.service.repository.BookMapper;
import com.example.library.service.storage.CoverData;
import com.example.library.service.storage.LocalFileStorage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookUploadService {

    private final BookMapper bookMapper;
    private final LocalFileStorage storage;

    public BookUploadService(BookMapper bookMapper, LocalFileStorage storage) {
        this.bookMapper = bookMapper;
        this.storage = storage;
    }

    @Transactional
    public UploadBookResult upload(String title, String author, String publisher,
                                   String isbn, String categories, String summary,
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
            if (cover == null) {
                coverUrl = "/static/default-cover.jpg";
            } else {
                coverUrl = storage.saveCover(cover);
            }
        } catch (Exception ex) {
            throw new BusinessException("UPLOAD_FAIL", "书籍封面保存失败");
        }

        UploadBookDto book = new UploadBookDto();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisher(publisher);
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

    @Transactional
    public boolean importCsv(InputStream in, Integer userId, String userName) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String header = br.readLine();
            if (header == null) throw new BusinessException("INVALID_FILE", "缺少表头");
            String[] heads = parseCsvLine(header);

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] cols = parseCsvLine(line);
                    String title = get(heads, cols, "title");
                    String author = get(heads, cols, "author");
                    String publisher = get(heads, cols, "publisher");
                    String isbn = get(heads, cols, "isbn");
                    String categories = get(heads, cols, "categories");
                    String summary = get(heads, cols, "summary");
                    upload(title, author, publisher, isbn, categories, summary, null, userId, userName);
                }
            }
            return true;
        } catch (Exception ex) {
            throw new BusinessException("IMPORT_FAIL", "批量导入失败：" + ex.getMessage());
        }
    }

    private String[] parseCsvLine(String s) {
        if (s == null || s.isEmpty()) return new String[0];
        // 去掉首尾的双引号，然后按","分割
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.substring(1, s.length() - 1);
        }
        return s.split("\",\"", -1);
    }

    private String get(String[] headers, String[] cols, String key) {
        for (int i = 0; i < headers.length; i++) {
            if (key.equalsIgnoreCase(headers[i].trim())) {
                return i < cols.length ? cols[i] : null;
            }
        }
        return null;
    }
}

