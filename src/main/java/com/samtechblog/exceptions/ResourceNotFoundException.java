package com.samtechblog.exceptions;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    private String ResourceName;
    private String FieldName;
    private int FieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Integer fieldValue) {
        super(String.format("%s not found with %s : %d", resourceName, fieldName, fieldValue));
        this.ResourceName = resourceName;
        this.FieldName = fieldName;
        this.FieldValue = fieldValue;
    }
}
