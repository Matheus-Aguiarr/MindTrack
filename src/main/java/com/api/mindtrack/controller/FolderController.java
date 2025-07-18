package com.api.mindtrack.controller;

import com.api.mindtrack.domain.folder.dto.FolderRequestDTO;
import com.api.mindtrack.domain.folder.dto.FolderResponseDTO;
import com.api.mindtrack.domain.note.dto.NoteResponseDTO;
import com.api.mindtrack.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("folder")
public class FolderController {

    @Autowired
    private FolderService folderService;

    @GetMapping("/user")
    public ResponseEntity<List<FolderResponseDTO>> getFoldersOfUser() {
        return ResponseEntity.ok(folderService.getFoldersOfUser());
    }

    @PostMapping()
    public ResponseEntity<FolderResponseDTO> postFolder(@RequestBody FolderRequestDTO data) {
        return  ResponseEntity.ok(folderService.postFolder(data));
    }

    @PostMapping("/{folderId}/subject/{subjectId}")
    public ResponseEntity<FolderResponseDTO> putSubjectInFolder(@PathVariable Long folderId, @PathVariable Long subjectId) {
        return  ResponseEntity.ok(folderService.putSubjectInFolder(folderId, subjectId));
    }

}
