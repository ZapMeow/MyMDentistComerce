package com.MyMDentis.MyMDentistComerce.Exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InvalidValuesProductException extends RuntimeException {

    private String code;
    private String attribute;

    public InvalidValuesProductException(String code, String attribute, String message) {
        super(message);
        this.code = code;
        this.attribute = attribute;
    }
}
