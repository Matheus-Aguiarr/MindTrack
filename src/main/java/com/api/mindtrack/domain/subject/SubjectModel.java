package com.api.mindtrack.domain.subject;

import com.api.mindtrack.domain.subject.dto.SubjectRequestDTO;
import com.api.mindtrack.domain.user.UserModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subjects")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class SubjectModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserModel user;


    public SubjectModel(SubjectRequestDTO dto, UserModel user) {
        this.name = dto.name();
        this.description = dto.description();
        this.user = user;
    }

}

