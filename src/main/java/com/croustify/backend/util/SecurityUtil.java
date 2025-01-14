package com.croustify.backend.util;

import com.croustify.backend.bean.CroustifyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public final class SecurityUtil {
    private SecurityUtil() {
    }
    public static CroustifyUserDetails getConnectedUserOrThrow() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CroustifyUserDetails) {
                return ((CroustifyUserDetails) principal);
            }
        }
        throw new IllegalStateException("Failed to retrieve connected user!");
    }

}