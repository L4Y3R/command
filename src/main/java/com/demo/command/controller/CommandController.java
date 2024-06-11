package com.demo.command.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/cmd")
public class CommandController {

    @PostMapping("/open")
    public String open() {
        return "Open";
    }

    @PostMapping("/close")
    public String close() {
        return "Close";
    }
}
