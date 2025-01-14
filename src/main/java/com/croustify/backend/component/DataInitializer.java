package com.croustify.backend.component;

import com.croustify.backend.repositories.RoleRepo;
import com.croustify.backend.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepository;


    @Override
    public void run(String... args) throws Exception {
        // Verifier si les roles existent sinon les creer
        if(roleRepository.findByName("ROLE_ADMIN") == null){
            roleRepository.save(new Role("ROLE_ADMIN"));
        }
        if(roleRepository.findByName("ROLE_USER") == null){
            roleRepository.save(new Role("ROLE_USER"));
        }
        if(roleRepository.findByName("ROLE_CUSTOMER") == null){
            roleRepository.save(new Role("ROLE_CUSTOMER"));
        }
        if(roleRepository.findByName("ROLE_FOOD_TRUCK_OWNER") == null){
            roleRepository.save(new Role("ROLE_FOOD_TRUCK_OWNER"));
        }
        if(roleRepository.findByName("ROLE_LOCATION_OWNER") == null){
            roleRepository.save(new Role("ROLE_LOCATION_OWNER"));
        }

    }
}
