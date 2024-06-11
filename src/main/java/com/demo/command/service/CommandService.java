package com.demo.command.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommandService {
    public String control(CommandDTO commandDTO){
        return "hello";
    }
}
