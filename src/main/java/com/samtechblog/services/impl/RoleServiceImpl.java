package com.samtechblog.services.impl;

import com.samtechblog.exceptions.ResourceNotFoundException;
import com.samtechblog.exceptions.RoleNotFoundExceptionDemo;
import com.samtechblog.models.Role;
import com.samtechblog.payloads.RoleDto;
import com.samtechblog.repositories.RoleRepository;
import com.samtechblog.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Role role = this.roleDtoToRole(roleDto);
        role.setRoleName(roleDto.getRoleName());
        role.setRoleCreatedAt(new Date());
        role.setRoleUpdatedAt(new Date());
        Role saveRole = this.roleRepository.save(role);
        return modelMapper.map(saveRole, RoleDto.class);
    }

    @Override
    public RoleDto updateRole(RoleDto roleDto, Integer roleId) {

        //get the role by using role id from the db
        Role role = this.roleRepository.findById(roleId).orElseThrow(()->new ResourceNotFoundException("Role","Id",roleId));
        role.setRoleName(roleDto.getRoleName());
        role.setRoleUpdatedAt(new Date());
        Role saveRole = this.roleRepository.save(role);
        return modelMapper.map(saveRole, RoleDto.class);
    }

    @Override
    public void deleteRole(Integer roleId) {
        //get the role by using role id from the db
        Role role = this.roleRepository.findById(roleId).orElseThrow(()->new ResourceNotFoundException("Role","Id",roleId));
        this.roleRepository.delete(role);
    }

    @Override
    public List<RoleDto> getAllRole() {
        List<Role> roles = this.roleRepository.findAll();
        return roles.stream().map(role -> modelMapper.map(role, RoleDto.class)).collect(Collectors.toList());
    }

    @Override
    public RoleDto getRole(Integer roleId) {
        //get the role by using role id from the db
        Role role = this.roleRepository.findById(roleId).orElseThrow(()->new ResourceNotFoundException("Role","Id",roleId));
        return roleToRoleDto(role);
    }

    @Override
    public Role getRoleByRoleName(String roleName) {
        return this.roleRepository.findByRoleName(roleName).orElseThrow(()->new RoleNotFoundExceptionDemo("Role Not Found"));
    }

    public RoleDto roleToRoleDto(Role role){
        return modelMapper.map(role, RoleDto.class);
    }
    public Role roleDtoToRole(RoleDto roleDto){
        return modelMapper.map(roleDto, Role.class);
    }
}
