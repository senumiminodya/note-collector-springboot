package lk.ijse.notecollectorspringboot.service.impl;

import lk.ijse.notecollectorspringboot.dao.UserDAO;
import lk.ijse.notecollectorspringboot.dto.impl.UserDTO;
import lk.ijse.notecollectorspringboot.entity.impl.UserEntity;
import lk.ijse.notecollectorspringboot.secure.JWTAuthResponse;
import lk.ijse.notecollectorspringboot.secure.SignIn;
import lk.ijse.notecollectorspringboot.service.AuthService;
import lk.ijse.notecollectorspringboot.service.JWTService;
import lk.ijse.notecollectorspringboot.util.Mapping;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceIMPL implements AuthService {
    private final UserDAO userDAO; //RequiredArgsConstructor dala thiyana nisa auto constructor thrugh ingection ekak wenawa.
    private final Mapping mapping;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        //principle user kenek widiyata userwa register karanna (Security config - Authentication manager)
        //Database eke user innawada kiyala search karanna
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        var user = userDAO.findByEmail(signIn.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var generatedToken = jwtService.generateToken(user);
        return JWTAuthResponse.builder().token(generatedToken).build();

    }

    @Override
    public JWTAuthResponse signUp(UserDTO userDTO) {
        //save user
        UserEntity savedUser = userDAO.save(mapping.toUserEntity(userDTO));
        //generate a token and return it
        var generatedToken = jwtService.generateToken(savedUser);
        return JWTAuthResponse.builder().token(generatedToken).build();
    }

    @Override
    public JWTAuthResponse refreshToken(String accessToken) {
        //mulinma user kawda kiyala hoyaganna one.(extract username(email))
        var userName = jwtService.extractUserName(accessToken);
        //check the user availability in the DB
        var findUser = userDAO.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var refreshToken = jwtService.refreshToken(findUser);
        return JWTAuthResponse.builder().token(refreshToken).build();
    }
}
