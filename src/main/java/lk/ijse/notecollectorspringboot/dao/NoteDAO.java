package lk.ijse.notecollectorspringboot.dao;


import lk.ijse.notecollectorspringboot.entity.impl.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteDAO extends JpaRepository<NoteEntity, String> {
}
