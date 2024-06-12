package com.demo.command.service;

import com.demo.command.DTO.CommandDTO;
import com.demo.command.entity.Command;
import com.demo.command.exception.UnknownErrorException;
import com.demo.command.repository.CommandRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class CommandService {

    @Autowired
    private CommandRepo commandRepo;


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<CommandDTO> control(CommandDTO commandDTO) {
        boolean isDeviceExist = validateDevice(commandDTO.getDevice());
        boolean isUserExist = validateUser(commandDTO.getUser());

        try{
            if (!isDeviceExist || !isUserExist) {
                throw new IllegalArgumentException("Device does not exist");
            }
            commandRepo.save(modelMapper.map(commandDTO, Command.class));
            return ResponseEntity.ok(commandDTO);
        } catch (Exception e) {
            throw new UnknownErrorException();
        }
    }

    private boolean validateDevice(String deviceId) {
        String url = "http://localhost:9000/api/v1/devices/" + deviceId;
        try{
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e){
            return false;
        }
    }

    private boolean validateUser(String userId) {
        String url = "http://localhost:9000/api/v1/users/" + userId;
        try{
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e){
           return false;
        }
    }
}
