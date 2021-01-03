package peter.finalprojectparallel.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import peter.finalprojectparallel.exception.QuackAppException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import static io.jsonwebtoken.Jwts.parserBuilder;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/quackApp.jks");
            keyStore.load(resourceAsStream, "quackApp".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new QuackAppException("Error occurred while attempting to load keystore");
        }
    }

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("quackApp", "quackApp".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new QuackAppException("Error occurred in retrieving public key");
        }
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = parserBuilder()
                .setSigningKey(getPublicKey()).build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * token with private key is validated with public key
     * @param jwt
     * @return
     */
    public Boolean validateToken(String jwt) {
        parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(jwt);
        //if no errors are thrown in the above line of code the jwt has been successfully validated
        return true;
    }

    private PublicKey getPublicKey() {
        try {
            //returns public key from keyStore
            return keyStore.getCertificate("quackApp").getPublicKey();
        } catch (KeyStoreException e) {
            throw new QuackAppException("Error occurred in retrieving public key");
        }
    }
}
