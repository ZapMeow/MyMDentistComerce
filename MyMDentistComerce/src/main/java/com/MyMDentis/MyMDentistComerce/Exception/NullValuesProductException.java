package com.MyMDentis.MyMDentistComerce.Exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NullValuesProductException extends RuntimeException {

    private String code;

    public NullValuesProductException(String codeException, String message) {
        super(message);
        this.code = codeException;
    }


}
