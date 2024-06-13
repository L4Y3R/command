package com.demo.command.controller;

import com.demo.command.DTO.CommandDTO;
import com.demo.command.exception.UnknownErrorException;
import com.demo.command.service.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class CommandController {

    @Autowired
    private CommandService commandService;

    @PostMapping("/cmd")
    public String cmdRequest(@RequestBody CommandDTO commandDTO) {
        try{
            commandService.control(commandDTO);
            return "Command processed successfully";
        }catch(Exception e) {
            throw new UnknownErrorException();
        }
    }
}
