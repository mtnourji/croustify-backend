package com.example.rollingFoods.rollingFoodsApp.controllers;


import com.example.rollingFoods.rollingFoodsApp.dto.UserCredentialDTO;
import com.example.rollingFoods.rollingFoodsApp.models.UserCredential;
import com.example.rollingFoods.rollingFoodsApp.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:8686")
@RestController
public class UserController {

    private static final Logger logger =  LoggerFactory.getLogger(UserController.class);

    // Injecting the UserService
    @Autowired
    private UserService userService;




    @PostMapping("/register")
    ResponseEntity<UserCredentialDTO> registerUser(@RequestBody @Validated UserCredentialDTO userCredentialDTO) {
        logger.info("Registering User: {}", userCredentialDTO);
        UserCredentialDTO registerUser = userService.registerUser(userCredentialDTO);
        return ResponseEntity.ok(registerUser);
    }

    @PostMapping("/signIn")
    ResponseEntity<String> signIn(@RequestBody @Validated UserCredentialDTO userCredentialDTO) {
        logger.info("Signing In User: {}", userCredentialDTO);
        try {
            String signIn = userService.signIn(userCredentialDTO);
            logger.info("User Signed In: {}", signIn);
            return ResponseEntity.ok(signIn);
        } catch (BadCredentialsException e) {
            logger.error("Invalid credentials: {}", userCredentialDTO);
            throw new BadCredentialsException("Invalid email or password");
        }catch (Exception e) {
            logger.error("Error signing in user: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error signing in user");
        }
    }

    //Test jwt token
    @GetMapping("/contact")
    public String contact(){
        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication: {}", authentication);
        if (authentication == null || !authentication.isAuthenticated()) {
            logger.error("User is not authenticated");
            throw new RuntimeException("User is not authenticated");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        logger.info("UserDetails: {}", userDetails);
        if (userDetails == null) {
            logger.error("UserDetails is null");
            throw new RuntimeException("UserDetails is null");
        }*/
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
    //Validate account
    @GetMapping("/validateAccount")
    public ResponseEntity<String> validateToken(@RequestParam String token) {
        logger.info("Validating token: {}", token);
        userService.validateToken(token);
        return ResponseEntity.ok("Account is valid");
    }
    //Update password
    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody @Validated UserCredentialDTO userCredentialDTO) {
        logger.info("Updating password: {}", userCredentialDTO);
        userService.updatePassword(userCredentialDTO);
        return ResponseEntity.ok("Password updated successfully");
    }

    //Delete account
    @DeleteMapping("/deleteAccount")
    public ResponseEntity<String> deleteAccount(@RequestParam Long userCredentialId) {
        logger.info("Deleting account: {}", userCredentialId);
        userService.deleteAccount(userCredentialId);
        return ResponseEntity.ok("Account deleted successfully");
    }


}
