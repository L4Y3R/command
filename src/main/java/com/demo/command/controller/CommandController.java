package com.demo.command.controller;

import com.demo.command.DTO.CommandDTO;
import com.demo.command.service.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class CommandController {

    @Autowired
    private CommandService commandService;

    @PostMapping("/cmd")
    public ResponseEntity<Object> cmdRequest(@RequestBody CommandDTO commandDTO) {
        try{
            CommandDTO savedCommand = commandService.control(commandDTO).getBody();
            return ResponseEntity.ok(savedCommand);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

}
