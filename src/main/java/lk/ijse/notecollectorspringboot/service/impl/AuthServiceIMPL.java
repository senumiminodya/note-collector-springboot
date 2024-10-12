package lk.ijse.notecollectorspringboot.service.impl;

import lk.ijse.notecollectorspringboot.dto.impl.UserDTO;
import lk.ijse.notecollectorspringboot.secure.JWTAuthResponse;
import lk.ijse.notecollectorspringboot.secure.SignIn;
import lk.ijse.notecollectorspringboot.service.AuthService;

public class AuthServiceIMPL implements AuthService {
    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        return null;
    }

    @Override
    public JWTAuthResponse signUp(UserDTO userDTO) {
        return null;
    }

    @Override
    public JWTAuthResponse refreshToken(String accessToken) {
        return null;
    }
}
