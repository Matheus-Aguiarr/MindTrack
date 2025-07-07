package com.api.mindtrack.domain.subject;

import com.api.mindtrack.domain.note.NoteModel;
import com.api.mindtrack.domain.studygoal.GoalModel;
import com.api.mindtrack.domain.subject.dto.SubjectRequestDTO;
import com.api.mindtrack.domain.user.UserModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subjects")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class SubjectModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserModel user;

    @OneToMany(mappedBy = "subject")
    private List<GoalModel> goals = new ArrayList<>();

    @OneToMany(mappedBy = "subject")
    private List<NoteModel> notes = new ArrayList<>();


    public SubjectModel(SubjectRequestDTO dto, UserModel user) {
        this.name = dto.name();
        this.description = dto.description();
        this.user = user;
    }

}

