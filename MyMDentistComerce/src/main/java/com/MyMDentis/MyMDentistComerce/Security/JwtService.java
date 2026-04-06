package com.MyMDentis.MyMDentistComerce.Security;

import com.MyMDentis.MyMDentistComerce.Model.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    final String KEY = "A_RANDOM_CHARACTERS_GROUP_LMAO_ONLY_IS_TO_TEST";
    final long TOKEN_EXPIRATION = 1000 * 60 * 60 * 5;    //five hours to expiration
    final long TOKEN_RENOVATION = 1000 * 60 * 60 * 24 * 2; // two days to renovation

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(KEY.getBytes());
    }

    public String generateToken(String username, Roles role){

        return Jwts.builder()
                .subject(username)                                                       //username
                .claim("role", role)                                                  //add role
                .issuedAt(new Date())                                                    //creation
                .expiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))     //expiration
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    public String extractUsername(String token){
        try{
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        }catch (JwtException eJwt){
            eJwt.printStackTrace();
            return null;
        }
    }


    public String extractRole(String token){
        try {
            Claims c = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return c.get("role", String.class);
        }catch (JwtException jwtException){
            jwtException.printStackTrace();
            return null;
        }
    }

    public boolean validToken(String token){

        try{
            Claims c = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            Date expirationTime = c.getExpiration();
            return expirationTime != null && expirationTime.after(new Date());

        }catch (JwtException jwtException){
            jwtException.printStackTrace();
            return false;
        }
    }


}
