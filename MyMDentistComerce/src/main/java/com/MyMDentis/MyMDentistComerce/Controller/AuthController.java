package com.MyMDentis.MyMDentistComerce.Controller;

import com.MyMDentis.MyMDentistComerce.DTO.DTOCredentials;
import com.MyMDentis.MyMDentistComerce.DTO.DTOJwt;
import com.MyMDentis.MyMDentistComerce.DTO.DTOUserEntity;
import com.MyMDentis.MyMDentistComerce.Model.UserEntity;
import com.MyMDentis.MyMDentistComerce.Security.JwtService;
import com.MyMDentis.MyMDentistComerce.Service.UserEntityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/MyMDentalCommerce/session")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserEntityService userEntityService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserEntityService userEntityService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userEntityService = userEntityService;
    }

    //MOVE THIS METHOD TO SERVICE
    @PostMapping(path = "/register")
    public ResponseEntity<DTOUserEntity> registerUser(@RequestBody DTOUserEntity dtoUserEntity){

        return ResponseEntity.ok(userEntityService.createUser(dtoUserEntity));

    }

    @PostMapping(path = "/login")
    public ResponseEntity<DTOJwt> sessionUser(@RequestBody DTOCredentials dtoCredentials){

        try{

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dtoCredentials.getUsername(), dtoCredentials.getPassword())
            );
            System.out.println(authentication.isAuthenticated() + " " + dtoCredentials.getUsername() + " " + dtoCredentials.getPassword());
            if (authentication.isAuthenticated()){
                DTOUserEntity user = userEntityService.findUserByUsername(dtoCredentials.getUsername());
                System.out.println(user == null);

                String token = jwtService.generateToken(user.getNameUser(), user.getRole());

                return ResponseEntity.ok(DTOJwt.builder()
                        .token(token)
                        .username(user.getNameUser())
                        .role(user.getRole())
                        .build());
            }

        } catch (BadCredentialsException e) {
            System.out.println("user or password incorrect");
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error with the request");
        return ResponseEntity.badRequest().build();
    }

        return ResponseEntity.badRequest().build();
    }
}
