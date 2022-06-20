package com.samtechblog.controllers;


import com.samtechblog.configurations.JwtTokenUtil;
import com.samtechblog.models.User;
import com.samtechblog.payloads.JwtAuthRequest;
import com.samtechblog.payloads.JwtAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    //create a token
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createAuthenticationToken(@RequestBody JwtAuthRequest jwtAuthRequest) throws Exception
    {
        try
        {
            Authentication request = new UsernamePasswordAuthenticationToken(jwtAuthRequest.getEmail(), jwtAuthRequest.getPassword());
            Authentication authenticate = authenticationManager.authenticate(request);

            SecurityContextHolder.getContext().setAuthentication(authenticate);

            System.out.println("Successfully authenticated. Security context contains: " +
                    SecurityContextHolder.getContext().getAuthentication());

            User user = (User) authenticate.getPrincipal();

            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
            if(user!=null)
            {
                String token = this.jwtTokenUtil.generateToken(user);
                System.out.println("my jwt token is "+token);
                jwtAuthResponse.setJwtToken(token);
            }
            return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse, HttpStatus.OK);
        }
        catch (DisabledException e)
        {
            throw new Exception("USER_DISABLED", e);
        }
        catch (BadCredentialsException e)
        {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        catch(AuthenticationException e)
        {
            throw new Exception("Authentication failed", e);
        }
    }
}
