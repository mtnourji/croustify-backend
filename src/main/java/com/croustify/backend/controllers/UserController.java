package com.croustify.backend.controllers;


import com.croustify.backend.bean.UserRole;
import com.croustify.backend.dto.*;
import com.croustify.backend.models.User;
import com.croustify.backend.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UserController {
    private static final Logger logger =  LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(){
        return ResponseEntity.ok(userService.getProfile());
    }

    @PutMapping("/profile")
    public ResponseEntity<Void> updateProfile(@RequestBody UpdateProfileDTO updateProfile){
        userService.updateProfile(updateProfile);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/register")
    ResponseEntity<Void> registerUser(@RequestBody @Validated UserLoginDTO userCredentialDTO) {
        logger.info("Registering User: {}", userCredentialDTO.getEmail());
        userService.registerUser(userCredentialDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        userService.resetPassword(resetPasswordDTO.getToken(), resetPasswordDTO.getPassword());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/signIn")
    ResponseEntity<LoginResponse> signIn(@RequestBody @Validated UserLoginDTO userLogin) {
        logger.info("Signing In User: {}", userLogin.getEmail());
        try {
            String token = userService.signIn(userLogin);
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (BadCredentialsException e) {
            logger.info("Invalid credentials: {}", userLogin.getEmail());
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    @GetMapping("/contact")
    public String contact(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
            String username = userDetails.getUsername();
            logger.info("Username: {}", username);
            logger.info("Email: {}", userDetails);
            logger.info("Roles: {}", userDetails.getAuthorities());
            return "Welcome to Rolling Foods " + username + " " + userDetails.getAuthorities()+ " " + userDetails;

        } else if (principal instanceof String){
            String username = (String) principal;
            logger.info("Username: {}", username);
            return "Welcome to Rolling Foods " + username;
        }
        else {
            throw new RuntimeException("Principal is not an instance of UserDetails");
        }
    }
    @GetMapping("/validateAccount")
    public ResponseEntity<String> validateToken(@RequestParam String token) {
        logger.info("Validating token: {}", token);
        userService.validateToken(token);
        return ResponseEntity.ok("Account is valid");
    }

    @Deprecated
    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody @Validated UserCredentialDTO userCredentialDTO) {
        logger.info("Updating password: {}", userCredentialDTO);
        userService.updatePassword(userCredentialDTO);
        return ResponseEntity.ok("Password updated successfully");
    }

    @Deprecated
    @DeleteMapping("/deleteAccount")
    public ResponseEntity<String> deleteAccount(@RequestParam Long userCredentialId) {
        logger.info("Deleting account: {}", userCredentialId);
        userService.deleteAccount(userCredentialId);
        return ResponseEntity.ok("Account deleted successfully");
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers(
            @RequestParam(required = false) UserRole userType,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) Boolean enabled
    ) {
        List<UserDTO> users = userService.findUsers(userType, email, firstName, lastName, enabled);
        return ResponseEntity.ok(users);
    }
}
