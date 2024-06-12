package com.demo.command.service;

import com.demo.command.DTO.CommandDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommandService {

    private static final Logger logger = LoggerFactory.getLogger(CommandService.class);

    public ResponseEntity<CommandDTO> control(CommandDTO commandDTO){
        return ResponseEntity.ok(commandDTO);
    }

    public String greet(){
        try {
            logger.info("[----Received Command---]");
            return "Hello World";
        }
        catch(Exception e){
            logger.error(e.getMessage());
        }
        return "Hello";
    }
}
