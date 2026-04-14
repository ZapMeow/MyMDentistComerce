package com.MyMDentis.MyMDentistComerce.Controller;

import com.MyMDentis.MyMDentistComerce.DTO.DTOUserEntity;
import com.MyMDentis.MyMDentistComerce.Model.UserEntity;
import com.MyMDentis.MyMDentistComerce.Service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/MyMDentalCommerce/users")
public class UserEntityController {

    @Autowired
    private UserEntityService userEntityService;


    @GetMapping(path = "/getUsers")
    public ResponseEntity<List<DTOUserEntity>> getAllUsers(){
        return ResponseEntity.ok(userEntityService.getAllUsers());
    }



}
