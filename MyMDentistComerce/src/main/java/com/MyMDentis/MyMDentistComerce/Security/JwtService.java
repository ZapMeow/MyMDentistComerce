package com.MyMDentis.MyMDentistComerce.Security;

import com.MyMDentis.MyMDentistComerce.Model.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret:g2af10tpolh4mC5Fo9fuLG9k8RwWuPMHZxWaJd9CWL1j9ipChh}")
    private String KEY_JWT;
    private final long EXPIRATION_TIME = 1000 * 60 * 5;
    private final long RENOVATION_TIME = 1000L * 60 * 60 * 24 * 3;


    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(KEY_JWT.getBytes());
    }


    public String generateToken(String username, Roles rol){
        return Jwts.builder()
                .subject(username)
                .claim("role", rol)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
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
        }catch (JwtException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public Roles extractRole(String token){
        try{
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.get("role", Roles.class);
        }catch (JwtException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public boolean validToken(String token){
        try{
            // Si el token está caducado o malformado, esto lanza un JwtException automáticamente
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            Date expiration = claims.getExpiration();
            return expiration != null && expiration.after(new Date());

        }catch (JwtException ex){
            ex.getMessage();
            return false;
        }
    }

}
