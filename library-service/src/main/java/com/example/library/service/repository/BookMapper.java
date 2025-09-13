package com.example.library.service.repository;

import com.example.library.core.dto.BookDetailDto;
import com.example.library.core.dto.FindBookDto;
import com.example.library.core.dto.UploadBookDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {

    /**
     * 分页查询
     * 模糊匹配查询书籍数量
     *
     */
    long countByKeywords(@Param("category") String category,
                         @Param("keywords") List<String> keywords);
    /**
     * 分页查询
     * 支持书名/作者的模糊匹配查询
     *
     */
    List<FindBookDto> find(@Param("category") String category,
                           @Param("keywords") List<String> keywords,
                           @Param("limit") int limit,
                           @Param("offset") int offset);

    /**
     * 根据 ISBN 查找书籍
     *
     */
    Integer getIdByIsbn(@Param("isbn") String isbn);

    /**
     * 插入新书籍
     *
     */
    int insertBook(UploadBookDto param);

    /**
     * 插入新书籍
     *
     */
    int insertInventory(@Param("bookId") int bookId,
                        @Param("ownerUserId") Integer ownerUserId,
                        @Param("createdBy") String createdBy);

    /**
     * 查询书籍详情及有效库存
     *
     */
    BookDetailDto getBookDetail(@Param("id") int id);
}
