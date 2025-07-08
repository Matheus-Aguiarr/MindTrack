package com.api.mindtrack.service;

import com.api.mindtrack.domain.note.NoteModel;
import com.api.mindtrack.domain.note.dto.NoteRequestDTO;
import com.api.mindtrack.domain.note.dto.NoteResponseDTO;
import com.api.mindtrack.domain.note.repository.NoteRepository;
import com.api.mindtrack.domain.studygoal.repository.GoalRepository;
import com.api.mindtrack.domain.subject.SubjectModel;
import com.api.mindtrack.domain.subject.repository.SubjectRepository;
import com.api.mindtrack.domain.user.UserModel;
import com.api.mindtrack.infra.exceptions.AccessDenied;
import com.api.mindtrack.infra.exceptions.SubjectNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private GoalRepository goalRepository;


    public NoteResponseDTO createNote(NoteRequestDTO data) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = (UserModel) authentication.getPrincipal();
        SubjectModel subject = subjectRepository.findById(data.subjectId()).orElseThrow(SubjectNotFound::new);
        if (!user.getId().equals(subject.getUser().getId())) {
            throw new AccessDenied();
        }

        NoteModel note = new NoteModel(data, subject);
        noteRepository.save(note);
        return new NoteResponseDTO(note);
    }

    public List<NoteResponseDTO> getNoteBySubjectId(Long subjectId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = (UserModel) authentication.getPrincipal();
        SubjectModel subject = subjectRepository.findById(subjectId).orElseThrow(SubjectNotFound::new);
        if (!user.getId().equals(subject.getUser().getId())) {
            throw new AccessDenied();
        }
        List<NoteModel> noteModelList = noteRepository.findAllBySubjectId(subjectId);

        return noteModelList.stream().map(NoteResponseDTO::new).toList();
    }
}

