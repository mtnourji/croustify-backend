package com.croustify.backend.config;


import com.croustify.backend.component.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true)
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public WebSecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .requestMatchers(HttpMethod.POST, "/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/reset-password").permitAll()
                        .requestMatchers(HttpMethod.POST, "/signIn").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/deleteAccount").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/updatePassword").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        .requestMatchers(HttpMethod.GET, "/google-certificates").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categories").permitAll()
                        .requestMatchers(HttpMethod.GET, "/foodTrucks/*/menus").permitAll()
                        .requestMatchers(HttpMethod.GET, "/menus").permitAll()
                        .requestMatchers(HttpMethod.GET, "/menus/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/foodTrucks").permitAll()
                        .requestMatchers(HttpMethod.GET, "/foodTrucks/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/validateAccount").permitAll()
                        .requestMatchers(HttpMethod.POST, "/items").permitAll()
                        .requestMatchers(HttpMethod.GET, "/items/foodTruck").permitAll()
                        .requestMatchers(HttpMethod.GET, "/items/category").permitAll()
                        .requestMatchers(HttpMethod.GET, "/items/foodTruckAndCategory").permitAll()
                        .requestMatchers(HttpMethod.GET, "/items/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/isFoodTruckOpen").permitAll()
                        .requestMatchers(HttpMethod.GET, "/images/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/foodTruckByFoodType").permitAll()
                        .requestMatchers(HttpMethod.GET, "/searchFoodTrucks").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/*/subscription/confirm").permitAll()
                        .requestMatchers(HttpMethod.POST, "/resend-verification-email").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/password/reset").permitAll()
                        .requestMatchers(HttpMethod.POST, "/password/change").permitAll()
                        .requestMatchers(HttpMethod.POST, "/password/validate-token").permitAll()
                        .requestMatchers(HttpMethod.POST, "/contacts").permitAll()
                        .requestMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui/**", "/webjars/**").permitAll()
                        .requestMatchers("/**").authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(Customizer.withDefaults())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
