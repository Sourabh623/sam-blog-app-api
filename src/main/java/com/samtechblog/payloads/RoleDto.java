package com.samtechblog.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private int roleId;
    private String roleName;
    private Date roleCreatedAt;
    private Date roleUpdatedAt;
}
