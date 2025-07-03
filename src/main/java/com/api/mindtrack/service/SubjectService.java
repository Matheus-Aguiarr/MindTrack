package com.api.mindtrack.service;

import com.api.mindtrack.domain.subject.SubjectModel;
import com.api.mindtrack.domain.subject.dto.SubjectRequestDTO;
import com.api.mindtrack.domain.subject.dto.SubjectResponseDTO;
import com.api.mindtrack.domain.subject.repository.SubjectRepository;
import com.api.mindtrack.domain.user.UserModel;
import com.api.mindtrack.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private UserRepository userRepository;

    public void createSubject(SubjectRequestDTO data) {
        UserModel findUser = userRepository.findById(data.user_id())
                .orElseThrow(() -> new RuntimeException("User Not Found."));
        SubjectModel subjectModel = new SubjectModel(data, findUser);
        subjectRepository.save(subjectModel);
    }

    public List<SubjectResponseDTO> getSubjectsByUserId(Long userId) {
        List<SubjectModel> allSubjectsOfUser = subjectRepository.findAllByUserId(userId);
        return allSubjectsOfUser.stream().map(SubjectResponseDTO::new).toList();
    }
}
