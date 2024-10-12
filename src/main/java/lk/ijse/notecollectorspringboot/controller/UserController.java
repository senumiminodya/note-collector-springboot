package lk.ijse.notecollectorspringboot.controller;

import lk.ijse.notecollectorspringboot.customStatusCode.SelectedUserAndNoteErrorStatus;
import lk.ijse.notecollectorspringboot.dto.UserStatus;
import lk.ijse.notecollectorspringboot.dto.impl.UserDTO;
import lk.ijse.notecollectorspringboot.exception.DataPersistException;
import lk.ijse.notecollectorspringboot.exception.UserNotFoundException;
import lk.ijse.notecollectorspringboot.service.UserService;
import lk.ijse.notecollectorspringboot.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    /* Methana multipart_form_data kiyanne MIME type ekak.
    multipart data ekaka parts godak enawa.
    e hama part ekema header and body ekak thiyanawa. e part ekak onama datatype ekakin thiyenna puluwan. */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    /* consume karanne client. produse karanne server. */
    public ResponseEntity<Void> saveUser(
        @RequestPart ("firstName") String firstName,
        @RequestPart ("lastName") String lastName,
        @RequestPart ("email") String email,
        @RequestPart ("password") String password,
        @RequestPart ("profilePic") MultipartFile profilePic /* mema multipartFile ekak dunnama data eka analyse karanna lesi */

    ){  //profilePic -----> Base64
        //Methana onnam regex walin validate karanna puluwan.
        String base64ProPic = "";
        try {
            byte [] bytesProPic = profilePic.getBytes();
            base64ProPic = AppUtil.profilePicToBase64(bytesProPic); /* byte file ekaka spring representation eka - base64 */
            //generate id
            String userId = AppUtil.generateUserId();
            //build the object
            var buildUserDto = new UserDTO();
            buildUserDto.setUserId(userId);
            buildUserDto.setFirstName(firstName);
            buildUserDto.setLastName(lastName);
            buildUserDto.setEmail(email);
            buildUserDto.setPassword(password);
            buildUserDto.setProfilePic(base64ProPic);
            userService.saveUser(buildUserDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserStatus getSelectedUser(@PathVariable ("userId") String userId) {
        //Methenta one nm regex eka danna puluwan. (id eke pattern eka)
        String regexForUserID = "^USER-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserID);
        var regexMatcher = regexPattern.matcher(userId);
        if (!regexMatcher.matches()) {
            return new SelectedUserAndNoteErrorStatus(1,"User ID is not valid");
        }
        return userService.getUser(userId);
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId) {
        String regexForUserID = "^USER-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserID);
        var regexMatcher = regexPattern.matcher(userId);
        try {
            if (!regexMatcher.matches()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (UserNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{userId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateUser(
            @RequestPart ("firstName") String firstName,
            @RequestPart ("lastName") String lastName,
            @RequestPart ("email") String email,
            @RequestPart ("password") String password,
            @RequestPart ("profilePic") MultipartFile profilePic,
            @PathVariable ("userId") String userId
    ) {  //profilePic -----> Base64
        String base64ProPic = "";
        try {
            byte [] bytesProPic = profilePic.getBytes();
            base64ProPic = AppUtil.profilePicToBase64(bytesProPic);
        }catch (Exception e) {
            e.printStackTrace();
        }
        //build the object
        var buildUserDto = new UserDTO();
        buildUserDto.setFirstName(firstName);
        buildUserDto.setLastName(lastName);
        buildUserDto.setEmail(email);
        buildUserDto.setPassword(password);
        buildUserDto.setProfilePic(base64ProPic);
        userService.updateUser(userId, buildUserDto);
    }
}
