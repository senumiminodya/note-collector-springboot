package lk.ijse.notecollectorspringboot.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

public interface JWTService {
    String extractUserName(String token);
    String generateToken(UserDetails userDetails);
    boolean validateToken(String token,UserDetails userDetails);
    String refreshToken(UserDetails userDetails);

}
