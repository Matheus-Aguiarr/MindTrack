package com.api.mindtrack.domain.folder.dto;

import com.api.mindtrack.domain.folder.FolderModel;
import com.api.mindtrack.domain.subject.SubjectModel;

import java.util.List;

public record FolderResponseDTO(Long id, String name, String color, List<SubjectModel> subjects) {

    public FolderResponseDTO(FolderModel folderModel) {
        this(
                folderModel.getId(),
                folderModel.getName(),
                folderModel.getColor(),
                folderModel.getSubjects()
        );
    }

}
