package com.demo.command.service;

import com.demo.command.DTO.CommandDTO;
import com.demo.command.entity.Command;
import com.demo.command.repository.CommandRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CommandService {

    @Autowired
    private CommandRepo commandRepo;

    @Autowired
    private WebClient.Builder webClientBuilder;


    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<CommandDTO> control(CommandDTO commandDTO) {
        boolean isUserExist = validateUser(commandDTO.getUserId());
       // boolean isDeviceExist = validateDevice(commandDTO.getDeviceId());

        if (!isUserExist) {
            throw new IllegalArgumentException("user na");
        }

        commandRepo.save(modelMapper.map(commandDTO, Command.class));
        return ResponseEntity.ok(commandDTO);
    }

    private boolean validateUser(String userId) {
        String url = "https://localhost:9000/api/v1/users/" + userId;
        try {
            Mono<Boolean> response = webClientBuilder.build()
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(Boolean.class);
            return response.blockOptional().orElse(false); // blocking call, handle it according to your application's needs
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private boolean validateDevice(String deviceId) {
        String url = "https://localhost:9000/api/v1/devices/" + deviceId;
        try {
            Mono<Boolean> response = webClientBuilder.build()
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(Boolean.class);
            return response.blockOptional().orElse(false); // blocking call, handle it according to your application's needs
        } catch (Exception e) {
            return false;
        }
    }

 /*
    public ResponseEntity<CommandDTO> command (CommandRequest commandRequest) {
        boolean isValidUser = validateUser(commandRequest.getUsername());
        boolean isValidDevice = validateDevice(commandRequest.getDevice());

        if (!isValidUser || !isValidDevice) {
            throw new IllegalArgumentException("Invalid user or device");
        }

        Command command = new Command();
        command.setUsername(commandRequest.getUsername());
        command.setDevice(commandRequest.getDevice());
        command.setCommand(commandRequest.getCommand());
        command.setTimestamp(LocalDateTime.now());

        return commandRepository.save(command);
    }

   // public ResponseEntity<CommandDTO> control(CommandDTO commandDTO){
   //     return ResponseEntity.ok(commandDTO);
   // }

     */
}
