package com.MyMDentis.MyMDentistComerce.Controller;

import com.MyMDentis.MyMDentistComerce.DTO.DTOUserEntity;
import com.MyMDentis.MyMDentistComerce.Service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/MyMDentalCommerce/users")
public class UserEntityController {

    @Autowired
    private UserEntityService userEntityService;

    @GetMapping(path = "/getUsers")
    public ResponseEntity<List<DTOUserEntity>> getAllUsers()throws InterruptedException{
        Thread.sleep(2000L);
        return ResponseEntity.ok(userEntityService.getAllUsers());
    }

    @PutMapping(path = "/updateUser/{email}")
    @PreAuthorize("permitAll()" )
    public ResponseEntity<DTOUserEntity> adminUpdateUser(@PathVariable String email,@RequestBody DTOUserEntity dto) throws InterruptedException{
        Thread.sleep(2000L);
        return ResponseEntity.ok(userEntityService.updateUser(email, dto, false));
    }
    @PutMapping(path = "/saveUSer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DTOUserEntity> adminUpdate(@PathVariable String email, @RequestBody DTOUserEntity dto) throws InterruptedException {
        Thread.sleep(2000L);
        return ResponseEntity.ok(userEntityService.adminUpdateUser(email, dto, false));
    }


}



