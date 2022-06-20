package com.samtechblog.services.impl;

import com.samtechblog.exceptions.ResourceNotFoundException;
import com.samtechblog.models.User;
import com.samtechblog.payloads.UserDto;
import com.samtechblog.repositories.UserRepository;
import com.samtechblog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public UserDto createUser(UserDto userDto) {
        //convert userDto to user
        User user = this.dtoToUser(userDto);

        user.setUserName(userDto.getUserName());
        user.setUserEmail(userDto.getUserEmail());
        user.setUserPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserAbout(userDto.getUserAbout());
        user.setUserCreatedAt(new Date());
        user.setUserUpdatedAt(new Date());

        //save user in the db
        User saveUser = this.userRepository.save(user);

        //return the userDto object instance of the client
        return this.userToUserDto(saveUser);
    }

    public UserDto updateUser(UserDto userDto, Integer userId) {

        //getting the user by userId from db
        User user = this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","Id", userId));

        //set the userDto data into user
        user.setUserName(userDto.getUserName());
        user.setUserEmail(userDto.getUserEmail());
        user.setUserAbout(userDto.getUserAbout());
        user.setUserUpdatedAt(new Date());

        //save the user in db
        User updatedUser = this.userRepository.save(user);

        //convert user to userDto
        return this.userToUserDto(updatedUser);
    }

    public UserDto getUserById(Integer userId) {

        //get the single user by id
        User user =  this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","Id", userId));

        //convert user to userDto and return to the client
        return this.userToUserDto(user);
    }

    public List<UserDto> getAllUsers() {
        //getting the users from the db
        List<User> users =  this.userRepository.findAll();

        //convert user list into user dto using stream api
        return users.stream().map(this::userToUserDto).collect(Collectors.toList());
    }

    public void deleteUser(Integer userId) {

        //getting the user by userId from db
        User user = this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","Id", userId));

        //delete the user into the db
        this.userRepository.delete(user);
    }

    //using model mapper lib to map userDto to user conversion here
    public User dtoToUser(UserDto userDto){
        return this.modelMapper.map(userDto, User.class);  //map(source, destination)
    }

    //using model mapper lib to map user to userDto conversion here
    public UserDto userToUserDto(User user){
        return this.modelMapper.map(user, UserDto.class);  //map(source, destination)
    }
}
