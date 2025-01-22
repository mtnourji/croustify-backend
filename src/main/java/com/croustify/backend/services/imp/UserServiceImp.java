package com.croustify.backend.services.imp;

import com.croustify.backend.bean.UserRole;
import com.croustify.backend.component.JwtTokenProvider;
import com.croustify.backend.dto.UserCredentialDTO;
import com.croustify.backend.dto.UserDTO;
import com.croustify.backend.dto.UserLoginDTO;
import com.croustify.backend.mappers.UserCredentialMapper;
import com.croustify.backend.models.PasswordResetToken;
import com.croustify.backend.models.Role;
import com.croustify.backend.models.User;
import com.croustify.backend.models.UserCredential;
import com.croustify.backend.repositories.PasswordResetTokenRepository;
import com.croustify.backend.repositories.RoleRepo;
import com.croustify.backend.repositories.UserCredentialRepo;
import com.croustify.backend.repositories.UserRepository;
import com.croustify.backend.services.UserService;
import com.croustify.backend.specifications.UserSpecifications;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImp implements UserService {

    private static final Logger logger =  LoggerFactory.getLogger(UserServiceImp.class);

    @Autowired
    private UserCredentialRepo userCredentialRepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCredentialMapper mapperUser;

    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    public UserServiceImp(PasswordEncoder passwordEncoder) {
    }

    @Override
    public void registerUser(UserLoginDTO userCredentialDTO) {
        if(userCredentialDTO.getPassword() ==  null || userCredentialDTO.getPassword().isEmpty()){
            throw new RuntimeException("Password is required");
        }else{
            UserCredential user = mapperUser.dtoToUser(userCredentialDTO);
            user.setUsername(user.getEmail());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepo.findByName("ROLE_USER"));
            user.setRoles(roles);
            userCredentialRepo.save(user);
        }
    }
    @Override
    public String signIn(UserLoginDTO userLogin) {

            try {
                UserCredential user = userCredentialRepo.findByEmail(userLogin.getEmail());
                if (user == null) {
                    throw new UsernameNotFoundException("User not found");
                }

                if("GOOGLE".equals(user.getAuthProvider())){
                    logger.info("User {} has signed in with Google", user.getUsername());
                }else{
                    if (!passwordEncoder.matches(userLogin.getPassword(), user.getPassword())) {
                        throw new BadCredentialsException("Invalid password");
                    }
                }

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
                logger.info("User {} has signed in", user.getUsername());
                return jwtTokenProvider.generateToken(authentication);

            } catch (Exception e) {
                throw new RuntimeException("Error: " + e.getMessage());
            }

    }

    @Transactional
    @Override
    public void resetPassword(String token, String newPassword){
        final PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired token"));

        if (!resetToken.isValid()) {
            throw new IllegalArgumentException("Token has expired or reached its max clicks.");
        }

        resetToken.incrementClickCount();
        tokenRepository.save(resetToken);

        UserCredential owner = resetToken.getUser();
        owner.setPassword(passwordEncoder.encode(newPassword));
        owner.setEnabled(true);
        tokenRepository.save(resetToken);
        userCredentialRepo.save(owner);
    }

    @Override
    public void validateToken(String token){
        try {
            // TODO REUSE JWTAuthFilter code
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

    @Override
    public List<UserDTO> findUsers(UserRole userType, String email, String firstName, String lastName, Boolean enabled) {
        Specification<User> spec = Specification.where(null);
        spec = spec.and(UserSpecifications.hasType(userType));

        spec = spec.and(UserSpecifications.hasEmail(email));
        spec = spec.and(UserSpecifications.hasLastName(lastName));
        spec = spec.and(UserSpecifications.hasFirstName(firstName));
        spec = spec.and(UserSpecifications.isEnabled(enabled));
        List<User> users = userRepository.findAll(spec);
        return mapperUser.rawUserToDto(users);
    }
}
