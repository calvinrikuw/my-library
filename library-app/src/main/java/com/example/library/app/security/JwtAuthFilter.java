package com.example.library.app.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer ";
    private final JwtUtils jwt;

    public JwtAuthFilter(JwtUtils jwt) {
        this.jwt = jwt;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        // 从请求头获取 Authorization
        String auth = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(auth) && auth.startsWith(BEARER)) {
            String token = auth.substring(BEARER.length()).trim();
            try {
                Map<String, Object> claims = jwt.parse(token);
                // 获取登录用户的唯一标识
                String subject = claims.getOrDefault("sub", "").toString();
                // 默认角色都是USER
                String role = (String) claims.getOrDefault("role", "USER");
                Integer uid = (Integer) claims.get("uid");
                String name = claims.getOrDefault("name", "system").toString();

                if (StringUtils.hasText(subject)) {
                    var authentication = new UsernamePasswordAuthenticationToken(subject, null, List.of(new SimpleGrantedAuthority("ROLE_" + role)));
                    authentication.setDetails(Map.of("uid", uid, "role", role, "name", name));
                    // 构建认证对象放入 Spring Security 上下文
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception ex) {
                throw new BadCredentialsException("Invalid token");
            }
        }
        chain.doFilter(req, res);
    }
}
