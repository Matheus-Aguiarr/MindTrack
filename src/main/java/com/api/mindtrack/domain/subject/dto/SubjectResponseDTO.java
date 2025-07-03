package com.api.mindtrack.domain.subject.dto;

import com.api.mindtrack.domain.subject.SubjectModel;

public record SubjectResponseDTO(String name, String description) {

    public SubjectResponseDTO(SubjectModel subjectModel) {
        this(
                subjectModel.getName(),
                subjectModel.getDescription()
        );
    }
}
