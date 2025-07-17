package com.api.mindtrack.domain.folder.repository;

import com.api.mindtrack.domain.folder.FolderModel;
import com.api.mindtrack.domain.folder.dto.FolderResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<FolderModel, Long> {

    List<FolderModel> findByUserId(Long id);
}
