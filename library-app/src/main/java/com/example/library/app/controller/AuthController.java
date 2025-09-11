package com.example.library.app.controller;

import com.example.library.app.dto.LoginRequest;
import com.example.library.app.dto.LoginResponse;
import com.example.library.app.dto.ResponseResult;
import com.example.library.app.handler.ResponseResults;
import com.example.library.app.security.JwtUtils;
import com.example.library.core.domain.User;
import com.example.library.service.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwt;

    public AuthController(AuthService authService, JwtUtils jwt) {
        this.authService = authService;
        this.jwt = jwt;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseResult<LoginResponse>> login(@Valid @RequestBody LoginRequest req) {
        User user = authService.validateLogin(req.getAccountOrEmail(), req.getPassword());
        String token = jwt.createAccessToken(
                user.getAccount(),
                Map.of("uid", user.getId(), "role", user.getRole(), "name", user.getUserName())
        );
        LoginResponse loginResponse = new LoginResponse(token, "Bearer", 3600, user.getUserName());
        return ResponseEntity.ok(ResponseResults.ok(loginResponse));
    }
}
