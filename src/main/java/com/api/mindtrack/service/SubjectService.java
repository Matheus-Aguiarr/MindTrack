package com.api.mindtrack.service;

import com.api.mindtrack.domain.studygoal.GoalModel;
import com.api.mindtrack.domain.studygoal.repository.GoalRepository;
import com.api.mindtrack.domain.subject.SubjectModel;
import com.api.mindtrack.domain.subject.dto.SubjectEditDTO;
import com.api.mindtrack.domain.subject.dto.SubjectRequestDTO;
import com.api.mindtrack.domain.subject.dto.SubjectResponseDTO;
import com.api.mindtrack.domain.subject.repository.SubjectRepository;
import com.api.mindtrack.domain.user.UserModel;
import com.api.mindtrack.domain.user.repository.UserRepository;
import com.api.mindtrack.infra.exceptions.SubjectNotFound;
import com.api.mindtrack.infra.exceptions.UserNotFound;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GoalRepository goalRepository;

    public SubjectResponseDTO createSubject(SubjectRequestDTO data) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = (UserModel) authentication.getPrincipal();

        UserModel findUser = userRepository.findById(user.getId())
                .orElseThrow(UserNotFound::new);
        SubjectModel subjectModel = new SubjectModel(data, findUser);
        subjectRepository.save(subjectModel);

        return new SubjectResponseDTO(subjectModel);
    }

    public List<SubjectResponseDTO> getSubjectsByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = (UserModel) authentication.getPrincipal();

        UserModel findUser = userRepository.findById(user.getId()).orElseThrow(UserNotFound::new);
        List<SubjectModel> allSubjectsOfUser = subjectRepository.findAllByUserId(user.getId());
        return allSubjectsOfUser.stream().map(SubjectResponseDTO::new).toList();
    }

    public SubjectResponseDTO getSubjectBySubjectId(Long subjectId) {
//        Busca os goals e o subject
        List<GoalModel> goalsOfSubject = goalRepository.findAllBySubjectId(subjectId);
        SubjectModel findSubject = subjectRepository.findById(subjectId)
                .orElseThrow(SubjectNotFound::new);

//        Pega o user autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = (UserModel) authentication.getPrincipal();

//        Verifica se o subject pertence ao usuario autenticado
        if(!findSubject.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso Negado.");
        }
        return new SubjectResponseDTO(findSubject, goalsOfSubject);
    }

    public SubjectResponseDTO editSubjectById(Long subjectId, SubjectEditDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = (UserModel) authentication.getPrincipal();


        SubjectModel findSubject = subjectRepository.findById(subjectId).orElseThrow(SubjectNotFound::new);

        if(!findSubject.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso Negado.");
        }

        if(dto.name() != null && !dto.name().isBlank()) {
            findSubject.setName(dto.name());
        }
        if(dto.description() != null && !dto.description().isBlank()) {
            findSubject.setDescription(dto.description());
        }
        subjectRepository.save(findSubject);

        return new SubjectResponseDTO(findSubject);

    }

    public void deleteSubjectById(Long subjectId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = (UserModel) authentication.getPrincipal();

        SubjectModel findSubject = subjectRepository.findById(subjectId).orElseThrow(SubjectNotFound::new);
        if (!findSubject.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso Negado.");
        }

        subjectRepository.deleteById(subjectId);
    }
}
