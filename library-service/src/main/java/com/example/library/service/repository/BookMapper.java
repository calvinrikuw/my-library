package com.example.library.service.repository;

import com.example.library.core.dto.FindBookDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {

    /**
     * 支持书名/作者的模糊匹配查询
     *
     */
    List<FindBookDto> find(@Param("keywords") List<String> keywords,
                           @Param("limit") int limit);
}
