package com.demo.command.controller;

import com.demo.command.DTO.CommandDTO;
import com.demo.command.exception.CommandNotSuccess;
import com.demo.command.service.CommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class CommandController {

    @Autowired
    private CommandService commandService;

    private static final Logger logger = LoggerFactory.getLogger(CommandController.class);

    @PostMapping("/cmd")
    public String cmdRequest(@RequestBody CommandDTO commandDTO) {
        try{
            logger.info("Command Received");
            commandService.control(commandDTO);
            return "Command processed successfully";
        }catch(Exception e) {
            logger.error("Command Error");
            throw new CommandNotSuccess();
        }
    }
}
