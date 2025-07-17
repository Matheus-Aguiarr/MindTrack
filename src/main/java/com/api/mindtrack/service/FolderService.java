package com.api.mindtrack.service;

import com.api.mindtrack.domain.folder.FolderModel;
import com.api.mindtrack.domain.folder.dto.FolderRequestDTO;
import com.api.mindtrack.domain.folder.dto.FolderResponseDTO;
import com.api.mindtrack.domain.folder.repository.FolderRepository;
import com.api.mindtrack.domain.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderService {

    @Autowired
    private FolderRepository folderRepository;


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
}
