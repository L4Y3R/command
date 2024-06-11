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
    public ResponseEntity<Command> open(@RequestBody Command command) {
        try{
            Command savedCommand = commandService.control(command);
            return ResponseEntity.ok(savedCommand);
        }catch{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
