package com.api.mindtrack.domain.note;

import com.api.mindtrack.domain.note.dto.NoteRequestDTO;
import com.api.mindtrack.domain.subject.SubjectModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "notes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class NoteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private LocalDate created_at;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private SubjectModel subject;


    public NoteModel(NoteRequestDTO dto, SubjectModel subject) {
        this.title = dto.title();
        this.content = dto.content();
        this.created_at = LocalDate.now();
        this.subject = subject;
    }

}
