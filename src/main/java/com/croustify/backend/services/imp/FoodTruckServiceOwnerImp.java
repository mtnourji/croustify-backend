package com.croustify.backend.services.imp;

import com.croustify.backend.dto.FoodTruckOwnerDTO;
import com.croustify.backend.dto.UserCredentialDTO;
import com.croustify.backend.repositories.FoodTruckOwnerRepo;
import com.croustify.backend.repositories.RoleRepo;
import com.croustify.backend.repositories.UserCredentialRepo;
import com.croustify.backend.mappers.AddressMapper;
import com.croustify.backend.mappers.FoodTruckOwnerMapper;
import com.croustify.backend.mappers.UserCredentialMapper;
import com.croustify.backend.models.FoodTruckOwner;
import com.croustify.backend.models.Role;
import com.croustify.backend.models.UserCredential;
import com.croustify.backend.models.embedded.Address;
import com.croustify.backend.services.EmailService;
import com.croustify.backend.services.FoodTruckOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class FoodTruckServiceOwnerImp implements FoodTruckOwnerService {

    //Injecting the AddressMapper
    @Autowired
    private AddressMapper addressMapper;

    // Injecting the FoodTruckOwnerRepo
    @Autowired
    private FoodTruckOwnerRepo foodTruckOwnerRepo;

    // Injecting the UserCredentialRepo
    @Autowired
    private UserCredentialMapper mapperUser;

    // Injecting the UserCredentialRepo
    @Autowired
    private UserCredentialRepo userCredentialRepo;

    // Injecting the FoodTruckOwnerMapper
    @Autowired
    private FoodTruckOwnerMapper mapper;

    // Injecting the PasswordEncoder
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private EmailService emailService;

    public FoodTruckServiceOwnerImp(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // Registering the FoodTruckOwner
    @Override
    public UserCredentialDTO registerFoodTruckUser(UserCredentialDTO userCredentialDTO) {
        if(userCredentialDTO.getPassword() ==  null || userCredentialDTO.getPassword().isEmpty()){
            throw new RuntimeException("Password est obligatoire");
        }else{
            UserCredential user = mapperUser.dtoToUser(userCredentialDTO);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepo.findByName("ROLE_FOOD_TRUCK_OWNER"));
            user.setRoles(roles);
            userCredentialRepo.save(user);
            return mapperUser.userToDto(user);

        }
    }

    // add a food truck owner to the database
    @Override
    public FoodTruckOwnerDTO addFoodTruckOwner(Long userCredentialId, FoodTruckOwnerDTO foodTruckOwnerDTO) {
        UserCredential userCredential = userCredentialRepo.findById(userCredentialId).orElseThrow(() -> new RuntimeException("User not found with id: " + userCredentialId));
        //Convert AddressDTO to Address
        Address address = addressMapper.dtoToAddress(foodTruckOwnerDTO.getAddress());

        FoodTruckOwner foodTruckOwner = new FoodTruckOwner();
        foodTruckOwner.setFirstname(foodTruckOwnerDTO.getFirstname());
        foodTruckOwner.setLastname(foodTruckOwnerDTO.getLastname());
        foodTruckOwner.setPhoneNumber(foodTruckOwnerDTO.getPhoneNumber());
        foodTruckOwner.setBankNumber(foodTruckOwnerDTO.getBankNumber());
        foodTruckOwner.setTva(foodTruckOwnerDTO.getTva());
        foodTruckOwner.setCompanyName(foodTruckOwnerDTO.getCompanyName());
        foodTruckOwner.setAddress(address);
        foodTruckOwner.setUserCredential(userCredential);
        FoodTruckOwner foodTruckOwnerSave =  foodTruckOwnerRepo.save(foodTruckOwner);
        emailService.sendEmailConfirmation(foodTruckOwnerSave, userCredential);  ;

        return mapper.foodTruckOwnerToDto(foodTruckOwnerSave);
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
