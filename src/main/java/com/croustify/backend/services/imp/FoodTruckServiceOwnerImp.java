package com.croustify.backend.services.imp;

import com.croustify.backend.dto.FoodTruckOwnerDTO;
import com.croustify.backend.dto.NewFoodTruckOwnerDTO;
import com.croustify.backend.mappers.AddressMapper;
import com.croustify.backend.mappers.FoodTruckOwnerMapper;
import com.croustify.backend.mappers.UserCredentialMapper;
import com.croustify.backend.models.FoodTruckOwner;
import com.croustify.backend.models.PasswordResetToken;
import com.croustify.backend.models.Role;
import com.croustify.backend.models.UserCredential;
import com.croustify.backend.models.embedded.Address;
import com.croustify.backend.repositories.FoodTruckOwnerRepo;
import com.croustify.backend.repositories.PasswordResetTokenRepository;
import com.croustify.backend.repositories.RoleRepo;
import com.croustify.backend.repositories.UserCredentialRepo;
import com.croustify.backend.services.EmailService;
import com.croustify.backend.services.FoodTruckOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class FoodTruckServiceOwnerImp implements FoodTruckOwnerService {

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private FoodTruckOwnerRepo foodTruckOwnerRepo;

    @Autowired
    private UserCredentialMapper mapperUser;

    @Autowired
    private UserCredentialRepo userCredentialRepo;

    @Autowired
    private FoodTruckOwnerMapper mapper;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private EmailService emailService;

    public FoodTruckServiceOwnerImp(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void createFoodTruckOwner(NewFoodTruckOwnerDTO foodTruckOwnerRequest) {
        String tempPassword = UUID.randomUUID().toString().substring(0, 8);

        final UserCredential userCredential = new UserCredential();
        final Set<Role> roles = new HashSet<>();
        roles.add(roleRepo.findByName("ROLE_FOOD_TRUCK_OWNER"));
        final FoodTruckOwner foodTruckOwner = mapper.newFoodTruckOwnerToModel(foodTruckOwnerRequest);
        foodTruckOwner.setUserCredential(userCredential);
        userCredential.setPassword(passwordEncoder.encode(tempPassword));
        userCredential.setEnabled(false);
        userCredential.setUsername(foodTruckOwnerRequest.getEmail());
        userCredential.setEmail(foodTruckOwnerRequest.getEmail());
        userCredential.setRoles(roles);

        userCredentialRepo.save(userCredential);
        foodTruckOwnerRepo.save(foodTruckOwner);

        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(userCredential);
        token.setExpirationDate(LocalDateTime.now().plusHours(48));
        token.setMaxClicks(2);
        tokenRepository.save(token);
        // TODO SEND MAIL sendResetLink(foodTruckOwner.getEmail(), token.getToken());
    }

    @Override
    public Long findFoodTruckOwnerIdByUserCredentialId(Long userCredentialId) {
        return foodTruckOwnerRepo.findFoodTruckOwnerIdByUserCredentialId(userCredentialId);
    }

    @Override
    public Boolean isFoodTruckOwner(Long userCredentialId) {
        boolean isFoodTruckOwner = foodTruckOwnerRepo.existsByUserCredentialId(userCredentialId);
        return isFoodTruckOwner;
    }

}
