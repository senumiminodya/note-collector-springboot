package lk.ijse.notecollectorspringboot.controller;

import lk.ijse.notecollectorspringboot.dto.impl.UserDTO;
import lk.ijse.notecollectorspringboot.exception.DataPersistException;
import lk.ijse.notecollectorspringboot.secure.JWTAuthResponse;
import lk.ijse.notecollectorspringboot.secure.SignIn;
import lk.ijse.notecollectorspringboot.service.UserService;
import lk.ijse.notecollectorspringboot.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("api/v1/auth/")
@RestController
@RequiredArgsConstructor
public class AuthUserController {
    private final UserService userService;
    // private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    @PostMapping(value = "signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JWTAuthResponse> saveUser(
            @RequestPart("firstName") String firstName,
            @RequestPart("lastName") String lastName,
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart("profilePic") MultipartFile profilePic
    ) {
        // profilePic ----> Base64
        String base64ProPic = "";
        try {
            byte[] bytesProPic = profilePic.getBytes();
            base64ProPic = AppUtil.profilePicToBase64(bytesProPic);
            //UserId generate
            String userId = AppUtil.generateUserId();
            //Build the Object
            UserDTO buildUserDTO = new UserDTO();
            buildUserDTO.setUserId(userId);
            buildUserDTO.setFirstName(firstName);
            buildUserDTO.setLastName(lastName);
            buildUserDTO.setEmail(email);
            buildUserDTO.setPassword(password);
            buildUserDTO.setProfilePic(base64ProPic);
            //Todo: Chane with auth user service
            userService.saveUser(buildUserDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("signin")
    public ResponseEntity<JWTAuthResponse> signIn(@RequestBody SignIn signIn){
        //
    }
    @PostMapping("refresh")
    public ResponseEntity<JWTAuthResponse> signIn(@RequestParam ("refreshToken") String refreshToken) {
        //
    }

}
