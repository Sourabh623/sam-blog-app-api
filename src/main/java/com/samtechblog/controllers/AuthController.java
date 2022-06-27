package com.samtechblog.controllers;


import com.samtechblog.configurations.JwtTokenUtil;
import com.samtechblog.exceptions.ApiException;
import com.samtechblog.exceptions.UserNotFoundException;
import com.samtechblog.payloads.JwtAuthRequest;
import com.samtechblog.payloads.JwtAuthResponse;
import com.samtechblog.payloads.UserDto;
import com.samtechblog.services.UserService;
import com.samtechblog.services.impl.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Autowired
    UserService userService;

    //create a token
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createAuthenticationToken(@RequestBody JwtAuthRequest jwtAuthRequest) throws Exception
    {
        this.authenticate(jwtAuthRequest.getUsername(),jwtAuthRequest.getPassword());
        UserDetails userDetails = this.customUserDetailService.loadUserByUsername(jwtAuthRequest.getUsername());
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        String token = jwtTokenUtil.generateToken(userDetails);
        //System.out.println("my jwt token is "+token);
        jwtAuthResponse.setJwtToken(token);
        return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse, HttpStatus.OK);
    }
    public void authenticate(String username, String password)
    {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username,password);
        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }catch (BadCredentialsException e){
            throw new ApiException("Invalid Password");
        }
    }

    //register user
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) throws RoleNotFoundException {
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }
}
