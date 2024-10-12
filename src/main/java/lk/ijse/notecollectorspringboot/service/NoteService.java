package lk.ijse.notecollectorspringboot.service;


import lk.ijse.notecollectorspringboot.dto.NoteStatus;
import lk.ijse.notecollectorspringboot.dto.impl.NoteDTO;

import java.util.List;

public interface NoteService {
    void saveNote(NoteDTO noteDTO);
    List<NoteDTO> getAllNotes();
    NoteStatus getNote(String noteId);
    void deleteNote(String noteId);
    void updateNote(String noteId, NoteDTO noteDTO);
}
