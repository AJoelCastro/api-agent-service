package com.arturocastro.apiagentservice.controller;

import com.arturocastro.apiagentservice.service.AgentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/openai/agent")
public class AgentControlller {

    private final AgentService agentService;

    public AgentControlller(AgentService agentService){
        this.agentService = agentService;
    }

    @PostMapping("/test")
    public ResponseEntity<?> agentBuilder(){
        agentService.agentBuilder();
        return ResponseEntity.ok().build();
    }

}
