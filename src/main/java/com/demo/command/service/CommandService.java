package com.demo.command.service;

import com.demo.command.DTO.CommandDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommandService {

    public ResponseEntity<CommandDTO> control(CommandDTO commandDTO){
        return ResponseEntity.ok(commandDTO);
    }
}
