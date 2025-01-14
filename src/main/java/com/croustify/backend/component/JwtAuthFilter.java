package com.croustify.backend.component;

import com.croustify.backend.bean.CroustifyUserDetails;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {


    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if (jwt != null && jwtTokenProvider.validateToken(jwt)) {
                //extract all claims from token
                Map<String, Object> claims = jwtTokenProvider.getTokens(jwt);
                String username = (String) claims.get("username");
                String email = (String) claims.get("email");
                Long id = (Long) claims.get("id");
                List<String> roles = new ArrayList<>();
                Object rolesObject = claims.get("roles");
                if (rolesObject instanceof List) {
                    for (Object role : (List) rolesObject) {
                        if (role instanceof String) {
                            roles.add((String) role);
                        } else if (role instanceof LinkedHashMap<?, ?>) {
                            String roleString = ((LinkedHashMap<String, String>) role).get("authority");
                            roles.add(roleString);
                        }
                    }
                }
                boolean enabled = (boolean) claims.get("enabled");
                if(!enabled){
                    // TODO Respond UNAUTHORIZED
                }
                //Verifications of claims
                if (username == null || email == null || roles.isEmpty()) {
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid JWT token");
                    return;
                }
                //Create user details
                UserDetails userDetails = new CroustifyUserDetails(id, username, "", roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

                //Create authentication
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (JwtException e) {
            SecurityContextHolder.clearContext();
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid JWT token");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
