package com.api.mindtrack.domain.subject.dto;

import com.api.mindtrack.domain.studygoal.GoalModel;
import com.api.mindtrack.domain.studygoal.dto.GoalResponseDTO;
import com.api.mindtrack.domain.subject.SubjectModel;

import java.util.List;

public record SubjectResponseDTO(String name, String description, List<GoalResponseDTO> goals) {

    public SubjectResponseDTO(SubjectModel subjectModel) {
        this(
                subjectModel.getName(),
                subjectModel.getDescription(),
                subjectModel.getGoals().stream().map(GoalResponseDTO::new).toList()
        );
    }

    public SubjectResponseDTO(SubjectModel subjectModel, List<GoalModel> goalModelList) {
        this(
                subjectModel.getName(),
                subjectModel.getDescription(),
                goalModelList.stream().map(GoalResponseDTO::new).toList()
        );
    }


}
