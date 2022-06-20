package com.samtechblog.services;
import com.samtechblog.payloads.RoleDto;
import java.util.List;

public interface RoleService {
    RoleDto createRole(RoleDto roleDto);
    RoleDto updateRole(RoleDto roleDto, Integer roleId);
    void deleteRole(Integer roleId);
    List<RoleDto> getAllRole();
    RoleDto getRole(Integer roleId);
}
