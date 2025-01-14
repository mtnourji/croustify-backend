package com.croustify.backend.services.imp;


import com.croustify.backend.repositories.UserCredentialRepo;
import com.croustify.backend.models.UserCredential;
import com.croustify.backend.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsServiceImp implements MyUserDetailsService {

    @Autowired
    private UserCredentialRepo userCredentialRepo;


    // This method is used to load the user by username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential user = userCredentialRepo.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
