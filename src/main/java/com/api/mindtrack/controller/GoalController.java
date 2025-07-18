package com.api.mindtrack.controller;

import com.api.mindtrack.domain.studygoal.dto.GoalEditDTO;
import com.api.mindtrack.domain.studygoal.dto.GoalRequestDTO;
import com.api.mindtrack.domain.studygoal.dto.GoalResponseDTO;
import com.api.mindtrack.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("goal")
public class GoalController {

//    ToDo: Marcar uma Meta como done; Fazer o Post retornar um 201 created.

    @Autowired
    private GoalService goalService;

    @PostMapping
    public ResponseEntity<GoalResponseDTO> createGoal(@RequestBody GoalRequestDTO data) {
        return ResponseEntity.ok(goalService.createGoal(data));
    }

    @PutMapping("/done/{goalId}")
    public ResponseEntity<GoalResponseDTO> markDoneGoal(@PathVariable Long goalId) {
        return ResponseEntity.ok(goalService.markDoneGoal(goalId));
    }

    @PutMapping("/pending/{goalId}")
    public ResponseEntity<GoalResponseDTO> markPendingGoal(@PathVariable Long goalId) {
        return ResponseEntity.ok(goalService.markPedingGoal(goalId));
    }

    @PatchMapping("/{goalId}")
    public ResponseEntity<GoalResponseDTO> editGoalById(@PathVariable Long goalId, @RequestBody GoalEditDTO data) {
        return ResponseEntity.ok(goalService.editGoalById(goalId, data));
    }

    @GetMapping()
    public ResponseEntity<List<GoalResponseDTO>> getGoalsOfSubject(@RequestParam Long subjectId) {
        return ResponseEntity.ok(goalService.getGoalsOfSubject(subjectId));
    }

    @DeleteMapping("/{goalId}")
    public ResponseEntity deleteGoalById(@PathVariable Long goalId) {
        goalService.deleteGoalById(goalId);
        return ResponseEntity.noContent().build();
    }

}
