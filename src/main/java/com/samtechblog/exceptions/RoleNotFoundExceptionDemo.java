package com.samtechblog.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoleNotFoundExceptionDemo extends RuntimeException{
    private String message;
}
