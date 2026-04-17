package com.MyMDentis.MyMDentistComerce.Controller;

import com.MyMDentis.MyMDentistComerce.Exception.DTOInvalidValuesException;
import com.MyMDentis.MyMDentistComerce.Exception.DTONullException;
import com.MyMDentis.MyMDentistComerce.Exception.InvalidValuesEntityException;
import com.MyMDentis.MyMDentistComerce.Exception.NullValuesEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = NullValuesEntityException.class)
    public ResponseEntity<DTONullException> nullExceptionHandler(NullValuesEntityException ex){
        DTONullException exception = DTONullException.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidValuesEntityException.class)
    public ResponseEntity<DTOInvalidValuesException> invalidValuesExceptionHandler(InvalidValuesEntityException ex){
        DTOInvalidValuesException exception = DTOInvalidValuesException.builder()
                .code(ex.getCode())
                .attribute(ex.getAttribute())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);

    }

}
