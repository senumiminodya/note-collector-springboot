package lk.ijse.notecollectorspringboot.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.notecollectorspringboot.customStatusCode.SelectedUserAndNoteErrorStatus;
import lk.ijse.notecollectorspringboot.dao.UserDAO;
import lk.ijse.notecollectorspringboot.dto.UserStatus;
import lk.ijse.notecollectorspringboot.dto.impl.UserDTO;
import lk.ijse.notecollectorspringboot.entity.impl.UserEntity;
import lk.ijse.notecollectorspringboot.exception.DataPersistException;
import lk.ijse.notecollectorspringboot.exception.UserNotFoundException;
import lk.ijse.notecollectorspringboot.service.UserService;
import lk.ijse.notecollectorspringboot.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserServiceIMPL implements UserService {
    @Autowired
    private UserDAO userDao;
    @Autowired
    private Mapping mapping;
    @Override
    public void saveUser(UserDTO userDTO) {
        UserEntity savedUser =
                userDao.save(mapping.toUserEntity(userDTO));
        if (savedUser == null) {
            throw new DataPersistException("User not saved");
        }
    }
    @Override
    public List<UserDTO> getAllUsers() {
        List<UserEntity> allUsers = userDao.findAll();
        return mapping.asUserDTOList(allUsers);
    }

    @Override
    public UserStatus getUser(String userId) {
        if(userDao.existsById(userId)){
            UserEntity selectedUser = userDao.getReferenceById(userId);
            return mapping.toUserDTO(selectedUser);
        }else {
            return new SelectedUserAndNoteErrorStatus(2, "User with id " + userId + " not found");
        }
    }

    @Override
    public void deleteUser(String userId) {
        Optional<UserEntity> existedUser = userDao.findById(userId);
        if(!existedUser.isPresent()){
            throw new UserNotFoundException("User with id " + userId + " not found");
        }else {
            userDao.deleteById(userId);
        }
    }

    @Override
    public void updateUser(String userId, UserDTO userDTO) {
        Optional<UserEntity> tmpUser = userDao.findById(userId);
        if(tmpUser.isPresent()) {
            tmpUser.get().setFirstName(userDTO.getFirstName());
            tmpUser.get().setLastName(userDTO.getLastName());
            tmpUser.get().setEmail(userDTO.getEmail());
            tmpUser.get().setPassword(userDTO.getPassword());
            tmpUser.get().setProfilePic(userDTO.getProfilePic());
        }
    }
}
