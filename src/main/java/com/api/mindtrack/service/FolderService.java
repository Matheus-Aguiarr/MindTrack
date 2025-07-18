package com.api.mindtrack.service;

import com.api.mindtrack.domain.folder.FolderModel;
import com.api.mindtrack.domain.folder.dto.FolderRequestDTO;
import com.api.mindtrack.domain.folder.dto.FolderResponseDTO;
import com.api.mindtrack.domain.folder.repository.FolderRepository;
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
public class FolderService {

    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private SubjectRepository subjectRepository;


    public List<FolderResponseDTO> getFoldersOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = (UserModel) authentication.getPrincipal();
        List<FolderModel> folders = folderRepository.findByUserId(user.getId());
        return folders.stream().map(FolderResponseDTO::new).toList();
    }


    public FolderResponseDTO postFolder(FolderRequestDTO data) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = (UserModel) authentication.getPrincipal();

        FolderModel folderModel = new FolderModel(data, user);
        folderRepository.save(folderModel);

        return new FolderResponseDTO(folderModel);
    }

    public FolderResponseDTO putSubjectInFolder(Long folderId, Long subjectId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = (UserModel) authentication.getPrincipal();
        SubjectModel subject = subjectRepository.findById(subjectId).orElseThrow(SubjectNotFound::new);

        if (!user.getId().equals(subject.getUser().getId())) {
            throw new AccessDenied();
        }

        FolderModel folder = folderRepository.findById(folderId).orElseThrow(() -> new RuntimeException("folder not found."));
        folder.setSubject(subject);
        subject.setFolder(folder);
        subjectRepository.save(subject);
        folderRepository.save(folder);

        return new FolderResponseDTO(folder);
    }
}
