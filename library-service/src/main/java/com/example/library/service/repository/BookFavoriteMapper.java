package com.example.library.service.repository;

import com.example.library.core.dto.BookDetailDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookFavoriteMapper {

    /**
     * 检查是否已收藏
     *
     */
    Integer existsFavorite(@Param("userId") Integer userId, @Param("bookId") Integer bookId);

    /**
     * 增加书籍收藏
     *
     */
    int insertFavorite(@Param("userId") Integer userId,
                       @Param("bookId") Integer bookId,
                       @Param("createdBy") String userName);

    /**
     * 取消书籍收藏
     *
     */
    int deleteFavorite(@Param("userId") Integer userId,
                       @Param("bookId") Integer bookId,
                       @Param("updatedBy") String userName);

    /**
     * 返回书籍收藏列表
     *
     */
    List<BookDetailDto> listMyFavorites(@Param("userId") Integer userId);
}

