package com.samtechblog.services;
import com.samtechblog.models.Role;
import com.samtechblog.payloads.RoleDto;
import javax.management.relation.RoleNotFoundException;
import java.util.List;

public interface RoleService {
    RoleDto createRole(RoleDto roleDto);
    RoleDto updateRole(RoleDto roleDto, Integer roleId);
    void deleteRole(Integer roleId);
    List<RoleDto> getAllRole();
    RoleDto getRole(Integer roleId);
    Role getRoleByRoleName(String roleName);
}
