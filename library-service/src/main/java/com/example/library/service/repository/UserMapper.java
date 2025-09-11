package com.example.library.service.repository;

import com.example.library.core.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    /**
     * 通过账号或邮箱查找用户
     */
    User findByAccountOrEmail(@Param("id") String accountOrEmail);
}
