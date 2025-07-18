package com.api.mindtrack.domain.folder.dto;

import com.api.mindtrack.domain.folder.FolderModel;
import com.api.mindtrack.domain.subject.SubjectModel;
import com.api.mindtrack.domain.subject.dto.SubjectResponseDTO;

import java.util.List;

public record FolderResponseDTO(Long id, String name, String color, List<SubjectResponseDTO> subjects) {

    public FolderResponseDTO(FolderModel folderModel) {
        this(
                folderModel.getId(),
                folderModel.getName(),
                folderModel.getColor(),
                folderModel.getSubjects().stream().map(SubjectResponseDTO::new).toList()
        );
    }

}
