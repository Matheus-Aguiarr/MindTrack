package com.api.mindtrack.service;

import com.api.mindtrack.domain.subject.SubjectModel;
import com.api.mindtrack.domain.subject.dto.SubjectEditDTO;
import com.api.mindtrack.domain.subject.dto.SubjectRequestDTO;
import com.api.mindtrack.domain.subject.dto.SubjectResponseDTO;
import com.api.mindtrack.domain.subject.repository.SubjectRepository;
import com.api.mindtrack.domain.user.UserModel;
import com.api.mindtrack.domain.user.repository.UserRepository;
import com.api.mindtrack.infra.exceptions.SubjectNotFound;
import com.api.mindtrack.infra.exceptions.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private UserRepository userRepository;

    public SubjectResponseDTO createSubject(SubjectRequestDTO data) {
        UserModel findUser = userRepository.findById(data.user_id())
                .orElseThrow(UserNotFound::new);
        SubjectModel subjectModel = new SubjectModel(data, findUser);
        subjectRepository.save(subjectModel);

        return new SubjectResponseDTO(subjectModel);
    }

    public List<SubjectResponseDTO> getSubjectsByUserId(Long userId) {
        UserModel findUser = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        List<SubjectModel> allSubjectsOfUser = subjectRepository.findAllByUserId(userId);
        return allSubjectsOfUser.stream().map(SubjectResponseDTO::new).toList();
    }

    public SubjectResponseDTO getSubjectBySubjectId(Long subjectId) {
        SubjectModel findSubject = subjectRepository.findById(subjectId)
                .orElseThrow(SubjectNotFound::new);
        return new SubjectResponseDTO(findSubject);
    }

    public SubjectResponseDTO editSubjectById(Long subjectId, SubjectEditDTO dto) {
        SubjectModel findSubject = subjectRepository.findById(subjectId).orElseThrow(SubjectNotFound::new);

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
        subjectRepository.deleteById(subjectId);
    }
}
