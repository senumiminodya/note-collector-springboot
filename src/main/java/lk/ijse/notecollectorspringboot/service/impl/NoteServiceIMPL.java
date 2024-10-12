package lk.ijse.notecollectorspringboot.service.impl;

import jakarta.transaction.Transactional;

import lk.ijse.notecollectorspringboot.customStatusCode.SelectedUserAndNoteErrorStatus;
import lk.ijse.notecollectorspringboot.dao.NoteDAO;
import lk.ijse.notecollectorspringboot.dto.NoteStatus;
import lk.ijse.notecollectorspringboot.dto.impl.NoteDTO;
import lk.ijse.notecollectorspringboot.entity.impl.NoteEntity;
import lk.ijse.notecollectorspringboot.exception.DataPersistException;
import lk.ijse.notecollectorspringboot.exception.NoteNotFoundException;
import lk.ijse.notecollectorspringboot.service.NoteService;
import lk.ijse.notecollectorspringboot.util.AppUtil;
import lk.ijse.notecollectorspringboot.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/* me layer eka athule thama business logic eka liyanne. */
/* controller layer eka service layer eka matha depend wenawa */

@Service /* Methenta @Annotation dannath puluwan. Meke service layer ekata adala functions godak ha */
@Transactional
public class NoteServiceIMPL implements NoteService {
    @Autowired
    private NoteDAO noteDao;
    @Autowired
    private Mapping noteMapping;

    @Override
    public void saveNote(NoteDTO noteDTO) {
        noteDTO.setNoteId(AppUtil.generateNoteId());
        NoteEntity savedNote =
                noteDao.save(noteMapping.toNoteEntity(noteDTO));
        if(savedNote == null){
            throw new DataPersistException("Note not saved");
        }
    }

    public List<NoteDTO> getAllNotes() {
        return noteMapping.asNoteDTOList(noteDao.findAll());
    }

    @Override
    public NoteStatus getNote(String noteId) {
        if(noteDao.existsById(noteId)){
            var selectedUser = noteDao.getReferenceById(noteId);
            return noteMapping.toNoteDTO(selectedUser);
        }else {
            return new SelectedUserAndNoteErrorStatus(2,"Selected note not found");
        }
    }

    @Override
    public void deleteNote(String noteId) {
        Optional<NoteEntity> existedUser = noteDao.findById(noteId);
        if (!existedUser.isPresent()) {
            throw new NoteNotFoundException("Note with id "+noteId+" not found!");
        }else {
            noteDao.deleteById(noteId);
        }
    }

    @Override
    public void updateNote(String noteId, NoteDTO noteDTO) {
        Optional<NoteEntity> findNote = noteDao.findById(noteId);
        if (!findNote.isPresent()){
            throw new NoteNotFoundException("Note not found!");
        } else {
            findNote.get().setNoteTitle(noteDTO.getNoteTitle());
            findNote.get().setNoteDesc(noteDTO.getNoteDesc());
            findNote.get().setCreatedDate(noteDTO.getCreatedDate());
            findNote.get().setPriorityLevel(noteDTO.getPriorityLevel());
        }
    }
}
