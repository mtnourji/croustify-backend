package com.example.rollingFoods.rollingFoodsApp.config;


import com.example.rollingFoods.rollingFoodsApp.component.JwtAuthFilter;
import com.example.rollingFoods.rollingFoodsApp.services.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final MyUserDetailsService myUserDetailsService;
    private final JwtAuthFilter jwtAuthFilter;

    public WebSecurityConfig(MyUserDetailsService myUserDetailsService, JwtAuthFilter jwtAuthFilter) {
        this.myUserDetailsService = myUserDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }


    // @Bean
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .requestMatchers(HttpMethod.GET, "/contact").permitAll()
                        .requestMatchers(HttpMethod.POST, "/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/signIn").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/deleteAccount").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/updatePassword").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        .requestMatchers(HttpMethod.GET, "/google-certificates").permitAll()
                        .requestMatchers(HttpMethod.POST, "/registerFoodTruckOwner").permitAll()
                        .requestMatchers(HttpMethod.POST,"/addFoodTruck").permitAll()
                        .requestMatchers(HttpMethod.GET, "/foodTruck").permitAll()
                        .requestMatchers(HttpMethod.GET, "/foodTruck/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/findFoodTruckOwnerIdByUserCredentialId").permitAll()
                        .requestMatchers(HttpMethod.GET,"/foodTruckByOwnerId").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/updateFoodTruck").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/deleteFoodTruck").permitAll()
                        .requestMatchers(HttpMethod.GET, "/isFoodTruckOwner").permitAll()
                        .requestMatchers(HttpMethod.GET, "/validateAccount").permitAll()
                        .requestMatchers(HttpMethod.POST, "/items").permitAll()
                        .requestMatchers(HttpMethod.GET, "/items/foodTruck").permitAll()
                        .requestMatchers(HttpMethod.GET, "/items/category").permitAll()
                        .requestMatchers(HttpMethod.GET, "/items/foodTruckAndCategory").permitAll()
                        .requestMatchers(HttpMethod.GET, "/items/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/items/{id}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/items/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/rateFoodTruck").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/openFoodTruck").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/closeFoodTruck").permitAll()
                        .requestMatchers(HttpMethod.GET, "/isFoodTruckOpen").permitAll()
                        .requestMatchers(HttpMethod.POST, "/uploadImage").permitAll()
                        .requestMatchers(HttpMethod.GET, "/images/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/favorite").permitAll()
                        .requestMatchers(HttpMethod.POST, "/favorite").permitAll()
                        .requestMatchers(HttpMethod.GET, "/isFavorite").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/favorite").permitAll()
                        .requestMatchers(HttpMethod.GET, "/foodTruckByFoodType").permitAll()
                        .requestMatchers(HttpMethod.GET, "/searchFoodTrucks").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/*/subscription/confirm").permitAll()
                        .requestMatchers(HttpMethod.POST, "/resend-verification-email").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/password/reset").permitAll()
                        .requestMatchers(HttpMethod.POST, "/password/change").permitAll()
                        .requestMatchers(HttpMethod.POST, "/password/validate-token").permitAll()
                        .requestMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui/**", "/webjars/**").permitAll()
                        .requestMatchers("/**").authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(Customizer.withDefaults())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    // UserDetailsService is an interface that can be implemented to provide user information.
    public UserDetailsService userDetailsService() {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("password").roles("USER").build());
        manager.createUser(users.username("admin").password("password").roles("USER", "ADMIN").build());
        return manager;
    }

    @Bean
    // public PasswordEncoder passwordEncoder()
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
