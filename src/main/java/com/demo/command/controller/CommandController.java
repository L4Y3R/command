package com.demo.command.controller;

import com.demo.command.service.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cmd")
public class CommandController {

    @Autowired
    private CommandService commandService;

    @PostMapping
    public ResponseEntity<?> open(@RequestBody CommandDTO commandDTO) {
        try{
            Command savedCommand = commandService.control(commandDTO);
            return ResponseEntity.created(commandDTO).body("Command Successful");
        }catch(Exception e){
            return ResponseEntity.internalServerError().body("Command could not be completed");
        }
    }

}
