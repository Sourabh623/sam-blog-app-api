package com.samtechblog.controllers;

import com.samtechblog.payloads.APIResponse;
import com.samtechblog.payloads.UserDto;
import com.samtechblog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") Integer userId)
    {
        UserDto getUserDto = this.userService.getUserById(userId);
        return new ResponseEntity<>(getUserDto, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUser()
    {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @PostMapping("/")     
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<APIResponse> deleteUser(@PathVariable("userId") Integer userId)
    {
        this.userService.deleteUser(userId);
        return new ResponseEntity<APIResponse>(new APIResponse("User is Deleted Successfully",true),HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable Integer userId, @RequestBody UserDto userDto)
    {
        UserDto updateUserDto = this.userService.updateUser(userDto,userId);
        return new ResponseEntity<>(updateUserDto, HttpStatus.OK);
    }

}
