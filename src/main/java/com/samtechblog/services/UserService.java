package com.samtechblog.services;
import com.samtechblog.payloads.UserDto;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto) throws RoleNotFoundException;
    UserDto updateUser(UserDto userDto, Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);
}
