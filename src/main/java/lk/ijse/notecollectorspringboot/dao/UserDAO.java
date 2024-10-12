package lk.ijse.notecollectorspringboot.dao;


import lk.ijse.notecollectorspringboot.entity.impl.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository /* Meya dao layer eke kenek kiyanna danne.
@componenet annotation eka meta annotate wela thiyenne */
public interface UserDAO extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
}
