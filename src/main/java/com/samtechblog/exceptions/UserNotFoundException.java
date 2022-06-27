package com.samtechblog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFoundException extends RuntimeException {

    private String _resourceName;
    private String _fieldName;
    private String _fieldValue;

    public UserNotFoundException(String _resourceName, String _fieldName, String _fieldValue) {
        super(String.format("%s not found with this %s : %s", _resourceName, _fieldName, _fieldValue));
        this._resourceName = _resourceName;
        this._fieldName = _fieldName;
        this._fieldValue = _fieldValue;
    }
}
