package com.demo.command.repository;

import com.demo.command.entity.Command;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommandRepo extends MongoRepository<Command, String> {
}
