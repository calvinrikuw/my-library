package com.example.library.app.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * 从 Security Context 中取得当前用户的 UID
     *
     * @return Uid
     */
    public static Integer getCurrentUid() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }
        Object details = auth.getDetails();
        if (details instanceof Map<?, ?> map) {
            Object v = map.get("uid");
            if (v instanceof Number n) {
                return n.intValue();
            }
        }
        return null;
    }

    public static String getCurrentName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }
        Object details = auth.getDetails();
        if (details instanceof Map<?, ?> map) {
            Object v = map.get("name");
            return v.toString();
        }
        return null;
    }
}
