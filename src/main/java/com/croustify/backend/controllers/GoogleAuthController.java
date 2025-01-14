package com.croustify.backend.controllers;

import com.croustify.backend.dto.UserCredentialDTO;
import com.croustify.backend.dto.UserDTO;
import com.croustify.backend.repositories.UserCredentialRepo;
import com.croustify.backend.component.JwtTokenProvider;
import com.croustify.backend.mappers.UserCredentialMapper;
import com.croustify.backend.models.UserCredential;
import com.croustify.backend.services.GoogleAuthService;
import com.croustify.backend.services.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:8686")
@RestController
public class GoogleAuthController {

    private static final Logger logger =  LoggerFactory.getLogger(UserController.class);

    @Autowired
    private GoogleAuthService googleAuthService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserCredentialRepo userCredentialRepo;

    @Autowired
    UserService userService;

    @Autowired
    UserCredentialMapper userCredentialMapper;


    @PostMapping("/auth")
    public ResponseEntity<?> googleAuth(@RequestBody Map<String, String> body) {

        try {
            String idToken = body.get("idToken");
            GoogleIdToken.Payload payload = googleAuthService.verifyToken(idToken);

            if (payload == null) {
                return ResponseEntity.badRequest().body("Invalid token");
            }

            UserCredential userCredentialExist = userCredentialRepo.findByEmail(payload.getEmail());

            if (userCredentialExist != null) {
                String jwtTokenGoogle = userService.signIn(userCredentialMapper.userToDto(userCredentialExist));
                return ResponseEntity.ok(jwtTokenGoogle);
            }

                String email = payload.getEmail();
                String username = payload.get("name").toString();
                String picture = payload.get("picture").toString();

                UserDTO userDTO = new UserDTO();
                userDTO.setEmail(email);
                userDTO.setFirstname(username);
                userDTO.setUrlProfilePicture(picture);

                UserCredentialDTO userCredentialDTO = new UserCredentialDTO();
                userCredentialDTO.setEmail(email);
                userCredentialDTO.setUsername(username);
                userCredentialDTO.setPassword("google");
                userCredentialDTO.setEnabled(true);
                userCredentialDTO.setUrlProfilePicture(picture);
                userCredentialDTO.setAuthProvider("GOOGLE");
                userService.registerUser(userCredentialDTO);


                String jwtTokenGoogle = userService.signIn(userCredentialDTO);

                return ResponseEntity.ok(jwtTokenGoogle);

        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

















    @GetMapping("/google-certificates")
    public ResponseEntity<String> testGoogleCertificates() {
        googleAuthService.testGoogleCertificatesAccess();
        return ResponseEntity.ok("Test completed. Check logs for results.");
    }

}
