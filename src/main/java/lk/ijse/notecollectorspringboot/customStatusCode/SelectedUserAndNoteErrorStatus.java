package lk.ijse.notecollectorspringboot.customStatusCode;


import lk.ijse.notecollectorspringboot.dto.NoteStatus;
import lk.ijse.notecollectorspringboot.dto.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectedUserAndNoteErrorStatus implements UserStatus, NoteStatus {
    private int status;
    private String statusMessage;
}
