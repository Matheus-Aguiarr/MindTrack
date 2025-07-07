package com.api.mindtrack.service;

import com.api.mindtrack.domain.studygoal.GoalModel;
import com.api.mindtrack.domain.studygoal.dto.GoalEditDTO;
import com.api.mindtrack.domain.studygoal.dto.GoalRequestDTO;
import com.api.mindtrack.domain.studygoal.dto.GoalResponseDTO;
import com.api.mindtrack.domain.studygoal.repository.GoalRepository;
import com.api.mindtrack.domain.subject.SubjectModel;
import com.api.mindtrack.domain.subject.repository.SubjectRepository;
import com.api.mindtrack.domain.user.UserModel;
import com.api.mindtrack.infra.exceptions.SubjectNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GoalService {

//    ToDo: Ao criar/editar um Goal para um subject especifico, eu apenas verifico o id do subject recebendo pela URL, mas, eu devo pegar o usuario autenticado, pra ai sim conferir se aquele subject eh dele, pra ai sim autorizar ou nao ele a fazer uma alteracao nesse subject.

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public GoalResponseDTO createGoal(GoalRequestDTO data) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = (UserModel) authentication.getPrincipal();

        SubjectModel findSubject = subjectRepository.findById(data.subject_id()).orElseThrow(SubjectNotFound::new);

        if (!user.getId().equals(findSubject.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso Negado.");
        }

        GoalModel goalModel = new GoalModel(data, findSubject);
        goalRepository.save(goalModel);
        return new GoalResponseDTO(goalModel);
    }


    public GoalResponseDTO markDoneGoal(Long goalId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = (UserModel) authentication.getPrincipal();

        GoalModel findGoal = goalRepository.findById(goalId).orElseThrow(() -> new RuntimeException("Goal Not Found."));
        SubjectModel findSubject = subjectRepository.findById(findGoal.getSubject().getId()).orElseThrow(SubjectNotFound::new);

        if (!user.getId().equals(findSubject.getUser().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso Negado.");
        }

        findGoal.setDone(true);
        goalRepository.save(findGoal);
        return new GoalResponseDTO(findGoal);
    }

    public List<GoalResponseDTO> getGoalsOfSubject(Long subjectId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = (UserModel) authentication.getPrincipal();
        SubjectModel findSubject = subjectRepository.findById(subjectId).orElseThrow(SubjectNotFound::new);

        if (!user.getId().equals(findSubject.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso Negado.");
        }

        List<GoalModel> findGoals = goalRepository.findAllBySubjectId(subjectId);
        return findGoals.stream().map(GoalResponseDTO::new).toList();
    }

    public GoalResponseDTO editGoalById(Long goalId, GoalEditDTO data) {
        GoalModel findGoal = goalRepository.findById(goalId).orElseThrow(() -> new RuntimeException("Goal Not Found."));
        SubjectModel findSubject = subjectRepository.findById(findGoal.getSubject().getId()).orElseThrow(SubjectNotFound::new);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = (UserModel) authentication.getPrincipal();

        if (!user.getId().equals(findSubject.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso Negado.");
        }

        if (data.title() != null && !data.title().isBlank()) {
            findGoal.setTitle(data.title());
        }
        if (data.description() != null && !data.description().isBlank()) {
            findGoal.setDescription(data.description());
        }
        if (data.deadline() != null && !data.deadline().toString().isBlank()) {
            findGoal.setDeadline(data.deadline());
        }
        goalRepository.save(findGoal);

        return new GoalResponseDTO(findGoal);
    }

    public void deleteGoalById(Long goalId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = (UserModel) authentication.getPrincipal();
        GoalModel goalModel = goalRepository.findById(goalId).orElseThrow(() -> new RuntimeException("Goal not found."));
        SubjectModel subjectModel = subjectRepository.findById(goalModel.getSubject().getId()).orElseThrow(SubjectNotFound::new);

        if (!user.getId().equals(subjectModel.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado.");
        }

        goalRepository.deleteById(goalId);
    }
}
