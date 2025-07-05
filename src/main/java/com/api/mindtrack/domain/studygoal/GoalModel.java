package com.api.mindtrack.domain.studygoal;

import com.api.mindtrack.domain.studygoal.dto.GoalRequestDTO;
import com.api.mindtrack.domain.subject.SubjectModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "goals")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class GoalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDate deadline;
    private Boolean done;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private SubjectModel subject;

    public GoalModel(GoalRequestDTO data, SubjectModel subject) {
        this.title = data.title();
        this.description = data.description();
        this.deadline = data.deadline();
        this.subject = subject;
        this.done = false;
    }
}
