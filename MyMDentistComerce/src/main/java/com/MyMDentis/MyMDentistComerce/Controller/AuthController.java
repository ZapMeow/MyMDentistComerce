package com.MyMDentis.MyMDentistComerce.Controller;

import com.MyMDentis.MyMDentistComerce.DTO.DTOCredentials;
import com.MyMDentis.MyMDentistComerce.DTO.DTOJwt;
import com.MyMDentis.MyMDentistComerce.DTO.DTOUserEntity;
import com.MyMDentis.MyMDentistComerce.Security.JwtService;
import com.MyMDentis.MyMDentistComerce.Service.UserEntityService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/MyMDentalCommerce/session")
public class AuthController {

    private final UserEntityService userEntityService;

    public AuthController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }


    @PostMapping(path = "/register")
    public ResponseEntity<DTOUserEntity> registerUser(@RequestBody DTOUserEntity dtoUserEntity) throws InterruptedException {
        Thread.sleep(2000L);
        return ResponseEntity.ok(userEntityService.createUser(dtoUserEntity));

    }

    @PostMapping(path = "/login")
    public ResponseEntity<DTOJwt> sessionUser(
            @RequestBody DTOCredentials dtoCredentials,
            HttpServletResponse response) throws InterruptedException {
        Thread.sleep(2000L);
        return ResponseEntity.ok(userEntityService.sessionUser(dtoCredentials, response));
    }
}
