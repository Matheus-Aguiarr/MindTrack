package com.api.mindtrack.controller;

import com.api.mindtrack.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("goal")
public class GoalController {

    @Autowired
    private GoalService goalService;

}
