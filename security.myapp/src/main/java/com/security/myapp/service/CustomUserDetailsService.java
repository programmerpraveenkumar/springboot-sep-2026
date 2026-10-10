package com.security.myapp.service;

import com.security.myapp.model.User;
import com.security.myapp.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    final UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo){
            this.userRepo=userRepo;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));

         return org.springframework.security.core.userdetails.User.
                 withUsername(user.getEmail())
                 .password(user.getPassword())
                 .roles(user.getRole())
                 .build();
    }

}
