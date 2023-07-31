package com.keremturak.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenManager {
    /**
     * 1- Kullanıcı kendini doğrulamalı, username ve password ile kendini doğrulamalı
     * 2- doğrulanmış kişinin kimlik bilgileri ile ana yeni bir JWT oluşturmalıyız.
     */
    private final Long exDate = 1000L * 20;
    private final String sifreAnahtari = "asdlasdkasd-lfdsgk565564asdf-LAKSDLKZX4C5258-***";

    public Optional<String> createToken(Long id){
        try {
            String token;
            token = JWT.create()
                    .withAudience()
                    //DİKKAT!! Lütfen buraya kişisel bilgiler eklemeyin ya da gizli olan bilgiler eklemeyin
                    //sifre, adres, email v.s. buraya eklenmemeli.
                    .withIssuer("kerem") // Token içinde gizli olmayan ancak kullanılması gerekn bilgiler
                    .withClaim("id", id)
                    .withClaim("islemturu", "genel")
                    .withIssuedAt(new Date()) //JWT tokenin oluşturulma zamanı time stamp
                    .withExpiresAt(new Date(System.currentTimeMillis() + exDate)) //JWT tokenin geçerlilik süresi. timestamp
                    .sign(Algorithm.HMAC512(sifreAnahtari)); // JWT tokenin şifreleme algoritması
            return Optional.of(token);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    public Optional<Long> getByIdFromToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC512(sifreAnahtari);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("kerem")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT==null)
                return Optional.empty();
            Long id = decodedJWT.getClaim("id").asLong();
            return Optional.of(id);
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
