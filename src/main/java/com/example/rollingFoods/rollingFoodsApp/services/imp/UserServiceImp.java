package com.example.rollingFoods.rollingFoodsApp.services.imp;

import com.example.rollingFoods.rollingFoodsApp.component.JwtTokenProvider;
import com.example.rollingFoods.rollingFoodsApp.controllers.CategorieController;
import com.example.rollingFoods.rollingFoodsApp.dto.UserCredentialDTO;
import com.example.rollingFoods.rollingFoodsApp.mappers.UserCredentialMapper;
import com.example.rollingFoods.rollingFoodsApp.models.Role;
import com.example.rollingFoods.rollingFoodsApp.models.UserCredential;
import com.example.rollingFoods.rollingFoodsApp.repositories.RoleRepo;
import com.example.rollingFoods.rollingFoodsApp.repositories.UserCredentialRepo;
import com.example.rollingFoods.rollingFoodsApp.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Set;




@Service
public class UserServiceImp implements UserService {

    private static final Logger logger =  LoggerFactory.getLogger(CategorieController.class);

    @Autowired
    private UserCredentialRepo userCredentialRepo;

    @Autowired
    private UserCredentialMapper mapperUser;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    public UserServiceImp(PasswordEncoder passwordEncoder) {
    }


    //Register user method
    @Override
    public UserCredentialDTO registerUser(UserCredentialDTO userCredentialDTO) {
        if(userCredentialDTO.getPassword() ==  null || userCredentialDTO.getPassword().isEmpty()){
            throw new RuntimeException("Password is required");
        }else{
            UserCredential user = mapperUser.dtoToUser(userCredentialDTO);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepo.findByName("ROLE_USER"));
            user.setRoles(roles);
            userCredentialRepo.save(user);
            return mapperUser.userToDto(user);

        }
    }
    //Sign in method
    @Override
    public String signIn(UserCredentialDTO userCredentialDTO) {

            try {
                UserCredential user = userCredentialRepo.findByEmail(userCredentialDTO.getEmail());
                if (user == null) {
                    throw new UsernameNotFoundException("User not found");
                }

                if("GOOGLE".equals(user.getAuthProvider())){
                    logger.info("User {} has signed in with Google", user.getUsername());
                }else{
                    if (!passwordEncoder.matches(userCredentialDTO.getPassword(), user.getPassword())) {
                        throw new BadCredentialsException("Invalid password");
                    }
                }

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
                logger.info("User {} has signed in", user.getUsername());
                String token =  jwtTokenProvider.generateToken(authentication);
                //return json token
                return "{\"token\": \"" + token + "\"}";

            } catch (Exception e) {
                throw new RuntimeException("Error: " + e.getMessage());
            }

    }

    @Override
    public void validateToken(String token){
        try {
            // Décodage du token
            Claims claims = Jwts.parser()
                    .setSigningKey("357638792F423F4428472B4B6250655368566D597133743677397A2443264629") // Utiliser la même clé secrète pour valider le JWT
                    .parseClaimsJws(token)
                    .getBody();

            // Vérification de l'action et récupération de l'ID de l'utilisateur
            String action = claims.get("action", String.class);
            Long userId = claims.get("id", Long.class);

            if ("activateAccount".equals(action)) {
                // Activer l'utilisateur dans la base de données
                UserCredential user = userCredentialRepo.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
                user.setEnabled(true);
                userCredentialRepo.save(user);
                System.out.println("Compte activé avec succès !");
            }
        } catch (Exception e) {
            throw new RuntimeException("Token invalide ou expiré", e);
        }
    }

    @Override
    public void updatePassword(UserCredentialDTO userCredentialDTO) {
        UserCredential user = userCredentialRepo.findByEmail(userCredentialDTO.getEmail());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        user.setPassword(passwordEncoder.encode(userCredentialDTO.getPassword()));
        userCredentialRepo.save(user);

    }

    @Override
    public void deleteAccount(Long userCredentialId) {
        UserCredential user = userCredentialRepo.findById(userCredentialId).orElseThrow(() -> new RuntimeException("User not found with id: " + userCredentialId));
        userCredentialRepo.delete(user);
    }


}
