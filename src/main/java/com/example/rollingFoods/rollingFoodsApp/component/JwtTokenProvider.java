package com.example.rollingFoods.rollingFoodsApp.component;


import com.example.rollingFoods.rollingFoodsApp.models.UserCredential;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtTokenProvider {

    public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";
    public static final int EXPIRATION = 864000000;

    // Generate token
    public String generateToken(Authentication authentication){
        UserCredential userDetails = (UserCredential) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDetails.getId());
        claims.put("username", userDetails.getUsername());
        claims.put("email", userDetails.getEmail());
        claims.put("roles", userDetails.getAuthorities());
        claims.put("enabled", userDetails.isEnabled());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();

    }

    public String generateTokenGoogle(String email){
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    // Validate token
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    // Get username email and roles from token
    public Map<String, Object> getTokens(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();

        Long id = claims.get("id", Long.class);
        String username = claims.get("username", String.class);
        String email = claims.get("email", String.class);
        List<String> roles = (List<String>) claims.get("roles");
        boolean enabled = claims.get("enabled", Boolean.class);

        Map<String, Object> tokenDetails = new HashMap<>();
        tokenDetails.put("id", id);
        tokenDetails.put("username", username);
        tokenDetails.put("email", email);
        tokenDetails.put("roles", roles);
        tokenDetails.put("enabled", enabled);

        return tokenDetails;
    }

    // Generate token for validation account of email
    public String generateTokenForValidationAccount(UserCredential user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("action", "activateAccount");

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

}
