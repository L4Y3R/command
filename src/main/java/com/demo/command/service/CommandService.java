package com.demo.command.service;

import com.demo.command.DTO.CommandDTO;
import com.demo.command.entity.Command;
import com.demo.command.repository.CommandRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class CommandService {

    @Autowired
    private CommandRepo commandRepo;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<CommandDTO> control(CommandDTO commandDTO) {
        boolean isDeviceExist = validateDevice(commandDTO.getDevice());

        if (!isDeviceExist) {
            throw new IllegalArgumentException("Device does not exist");
        }

        commandRepo.save(modelMapper.map(commandDTO, Command.class));
        return ResponseEntity.ok(commandDTO);
    }

    private boolean validateDevice(String deviceId) {
        String url = "http://localhost:9000/api/v1/devices/" + deviceId;

        // Make a GET request to the external service
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Check if the response indicates the device exists
        return response.getStatusCode() == HttpStatus.OK;
    }
}
