package com.example.library.service.service;

import com.example.library.core.dto.BookDetailDto;
import com.example.library.core.exception.BusinessException;
import com.example.library.service.repository.BookFavoriteMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookFavoriteService {
    private final BookFavoriteMapper favoriteMapper;

    public BookFavoriteService(BookFavoriteMapper favoriteMapper) {
        this.favoriteMapper = favoriteMapper;
    }

    @Transactional
    public void add(Integer userId, Integer bookId, String userName) {
        if (userId == null || bookId == null) {
            throw new BusinessException("INVALID_ARGUMENT", "用户或图书ID不能为空");
        }

        Integer count = favoriteMapper.existsFavorite(userId, bookId);
        if (count != null && count > 0) {
            throw new BusinessException("DUPLICATE_FAVORITE", "您已收藏过该书");
        }

        favoriteMapper.insertFavorite(userId, bookId, userName);
    }

    @Transactional
    public void remove(Integer userId, Integer bookId, String userName) {
        if (userId == null || bookId == null) {
            throw new BusinessException("INVALID_ARGUMENT", "用户或图书ID不能为空");
        }

        favoriteMapper.deleteFavorite(userId, bookId, userName);
    }

    public List<BookDetailDto> list(Integer userId) {
        if (userId == null) {
            throw new BusinessException("INVALID_ARGUMENT", "用户ID不能为空");
        }

        return favoriteMapper.listMyFavorites(userId);
    }
}

