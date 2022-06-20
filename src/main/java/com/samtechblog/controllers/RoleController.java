package com.samtechblog.controllers;

import com.samtechblog.payloads.APIResponse;
import com.samtechblog.payloads.RoleDto;
import com.samtechblog.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping("/")
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto)
    {
        RoleDto saveRoleDto = this.roleService.createRole(roleDto);
        return new ResponseEntity<RoleDto>(saveRoleDto, HttpStatus.CREATED);
    }
    @PutMapping("/{roleId}")
    public ResponseEntity<RoleDto> updateRole(@RequestBody RoleDto roleDto, @PathVariable Integer roleId)
    {
        RoleDto updateRoleDto = this.roleService.updateRole(roleDto, roleId);
        return new ResponseEntity<RoleDto>(updateRoleDto, HttpStatus.OK);
    }
    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDto> getRole(@PathVariable Integer roleId)
    {
        RoleDto roleDto = this.roleService.getRole(roleId);
        return new ResponseEntity<RoleDto>(roleDto, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<RoleDto>> getAllRole()
    {
        List<RoleDto> roleDtoList = this.roleService.getAllRole();
        return new ResponseEntity<>(roleDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<APIResponse> deleteRole(@PathVariable Integer roleId)
    {
        this.roleService.deleteRole(roleId);
        return new ResponseEntity<APIResponse>(new APIResponse("Role is Deleted SuccessFully",true),HttpStatus.OK);
    }

}
