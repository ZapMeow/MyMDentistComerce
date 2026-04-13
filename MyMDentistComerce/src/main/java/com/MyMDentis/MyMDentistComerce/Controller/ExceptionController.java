package com.MyMDentis.MyMDentistComerce.Controller;

import com.MyMDentis.MyMDentistComerce.Exception.DTOInvalidValuesException;
import com.MyMDentis.MyMDentistComerce.Exception.DTONullException;
import com.MyMDentis.MyMDentistComerce.Exception.InvalidValuesProductException;
import com.MyMDentis.MyMDentistComerce.Exception.NullValuesProductException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = NullValuesProductException.class)
    public ResponseEntity<DTONullException> nullExceptionHandler(NullValuesProductException ex){
        DTONullException exception = DTONullException.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidValuesProductException.class)
    public ResponseEntity<DTOInvalidValuesException> invalidValuesExceptionHandler(InvalidValuesProductException ex){
        DTOInvalidValuesException exception = DTOInvalidValuesException.builder()
                .code(ex.getCode())
                .attribute(ex.getAttribute())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);

    }

}
