package com.samtechblog.services.impl;

import com.samtechblog.exceptions.UserNotFoundException;
import com.samtechblog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    public UserRepository userRepository;

    //loading the user from the database by username(email)
    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
       return this.userRepository.findByUserEmail(username).orElseThrow(()->new UserNotFoundException("User","Email",username));
    }
}
