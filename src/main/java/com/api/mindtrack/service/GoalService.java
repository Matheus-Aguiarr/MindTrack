package com.api.mindtrack.service;

import com.api.mindtrack.domain.studygoal.GoalModel;
import com.api.mindtrack.domain.studygoal.dto.GoalRequestDTO;
import com.api.mindtrack.domain.studygoal.dto.GoalResponseDTO;
import com.api.mindtrack.domain.studygoal.repository.GoalRepository;
import com.api.mindtrack.domain.subject.SubjectModel;
import com.api.mindtrack.domain.subject.repository.SubjectRepository;
import com.api.mindtrack.infra.exceptions.SubjectNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public GoalResponseDTO createGoal(GoalRequestDTO data) {
        SubjectModel findSubject = subjectRepository.findById(data.subject_id()).orElseThrow(SubjectNotFound::new);
        GoalModel goalModel = new GoalModel(data, findSubject);
        goalRepository.save(goalModel);
        return new GoalResponseDTO(goalModel);
    }

    public GoalResponseDTO markDoneGoal(Long goalId) {
        GoalModel findGoal = goalRepository.findById(goalId).orElseThrow(() -> new RuntimeException("Goal Not Found."));
        findGoal.setDone(true);
        goalRepository.save(findGoal);
        return new GoalResponseDTO(findGoal);
    }
}
