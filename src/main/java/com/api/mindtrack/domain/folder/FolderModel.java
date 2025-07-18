package com.api.mindtrack.domain.folder;

import com.api.mindtrack.domain.folder.dto.FolderRequestDTO;
import com.api.mindtrack.domain.subject.SubjectModel;
import com.api.mindtrack.domain.user.UserModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "folders")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class FolderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String color; // HexaDecimal.

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubjectModel> subjects = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserModel user;

    public FolderModel(FolderRequestDTO folderRequestDTO, UserModel user) {
        this.name = folderRequestDTO.name();
        this.color = folderRequestDTO.color();
        this.user = user;
    }

    public void setSubject(SubjectModel subject) {
        this.subjects.add(subject);
    }
}
