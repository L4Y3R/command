package com.demo.command.service;

import com.demo.command.DTO.CommandDTO;
import com.demo.command.entity.Command;
import com.demo.command.exception.DeviceNotAuthorizedException;
import com.demo.command.exception.DeviceNotFoundException;
import com.demo.command.interfaces.DeviceClient;
import com.demo.command.repository.CommandRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@Service
public class CommandService {

    @Autowired
    private CommandRepo commandRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DeviceClient deviceClient;

    @Transactional
    public void control(CommandDTO commandDTO) {
        //check if the device exists with the user given
        boolean isDeviceExist = validateDevice(commandDTO.getDevice(), commandDTO.getUser());

        try{
            if (!isDeviceExist) {
                throw new DeviceNotFoundException();
            }

            Command command = modelMapper.map(commandDTO, Command.class);
            //save command for log
            commandRepo.save(command);

            String uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(command.getId())
                    .toUriString();

            ResponseEntity.created(new URI(uri)).build();
        } catch (Exception e) {
            throw new DeviceNotAuthorizedException();
        }
    }

    //validating with external service
    private boolean validateDevice(String deviceId, String userId) {
        try {
            ResponseEntity<String> response = deviceClient.validateDevice(deviceId, userId);
            //device exists with the user
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            return false;
        }
    }
}
