package com.api.mindtrack.domain.subject.dto;

import com.api.mindtrack.domain.note.NoteModel;
import com.api.mindtrack.domain.note.dto.NoteResponseDTO;
import com.api.mindtrack.domain.studygoal.GoalModel;
import com.api.mindtrack.domain.studygoal.dto.GoalResponseDTO;
import com.api.mindtrack.domain.subject.SubjectModel;

import java.util.List;

public record SubjectResponseDTO(String name, String description, List<GoalResponseDTO> goals, List<NoteResponseDTO> notes) {

    public SubjectResponseDTO(SubjectModel subjectModel) {
        this(
                subjectModel.getName(),
                subjectModel.getDescription(),
                subjectModel.getGoals().stream().map(GoalResponseDTO::new).toList(),
                subjectModel.getNotes().stream().map(NoteResponseDTO::new).toList()
        );
    }

    public SubjectResponseDTO(SubjectModel subjectModel, List<GoalModel> goalModelList, List<NoteModel> notes) {
        this(
                subjectModel.getName(),
                subjectModel.getDescription(),
                goalModelList.stream().map(GoalResponseDTO::new).toList(),
                notes.stream().map(NoteResponseDTO::new).toList()
        );
    }


}
