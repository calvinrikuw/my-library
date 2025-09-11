package com.example.library.service.service;

import com.example.library.core.domain.User;
import com.example.library.core.exception.BusinessException;
import com.example.library.service.repository.UserMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User validateLogin(String accountOrEmail, String rawPassword) {
        User user = userMapper.findByAccountOrEmail(accountOrEmail);
        if (user == null) throw new BusinessException("NOT_FOUND", "用户不存在");
        if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
            throw new BusinessException("USER_DISABLED", "账号已禁用");
        }
        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            throw new BusinessException("AUTH_FAILED", "账号或密码错误");
        }
        return user;
    }
}

