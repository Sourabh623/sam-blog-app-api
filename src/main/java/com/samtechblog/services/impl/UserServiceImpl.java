package com.samtechblog.services.impl;

import com.samtechblog.exceptions.ResourceNotFoundException;
import com.samtechblog.models.Role;
import com.samtechblog.models.User;
import com.samtechblog.payloads.RoleDto;
import com.samtechblog.payloads.UserDto;
import com.samtechblog.repositories.RoleRepository;
import com.samtechblog.repositories.UserRepository;
import com.samtechblog.services.RoleService;
import com.samtechblog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static com.samtechblog.configurations.AppConstants.NORMAL_USER;
import static com.samtechblog.configurations.AppConstants.NORMAL_USER_DEFAULT_ID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RoleService roleService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public UserDto createUser(UserDto userDto) throws NullPointerException, RoleNotFoundException {

        //convert userDto to user
        User user = this.dtoToUser(userDto);

        user.setUserFullName(userDto.getUserFullName());
        user.setUserEmail(userDto.getUserEmail());
        user.setUserPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserAbout(userDto.getUserAbout());
        user.setUserCreatedAt(new Date());
        user.setUserUpdatedAt(new Date());

        //first check user role available or not in the db
        //getting the role by roleName
        long recordsCount = this.roleRepository.count();
        //System.out.println("record count is "+recordsCount);
        Role role;
        if(recordsCount==0){
            //System.out.println("Record not found in the Table");
            Role newRole = new Role(NORMAL_USER_DEFAULT_ID,NORMAL_USER,new Date(),new Date());
            role = this.roleRepository.save(newRole);
        }
        else role = roleService.getRoleByRoleName(NORMAL_USER); //NORMAL

        Set<Role> roleSet = new HashSet<Role>();
        roleSet.add(role);
        //save user in the db
        User saveUser = this.userRepository.save(user);
        //set the role
        saveUser.setRoles(roleSet);
        //again save user
        this.userRepository.save(saveUser);
        //return the userDto object instance of the client
        return this.userToUserDto(saveUser);
    }

    public UserDto updateUser(UserDto userDto, Integer userId) {

        //getting the user by userId from db
        User user = this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","Id", userId));

        //set the userDto data into user
        user.setUserFullName(userDto.getUserFullName());
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

        //delete first role of the user
        //this.userRepository.deleteUserById(user.getUserId());

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
