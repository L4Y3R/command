package com.demo.command.service;

import com.demo.command.DTO.CommandDTO;
import com.demo.command.exception.DeviceNotAuthorizedException;
import com.demo.command.exception.DeviceNotFoundException;
import com.demo.command.interfaces.DeviceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommandService{

    @Autowired
    private DeviceClient deviceClient;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "command_topic";

    private static final Logger logger = LoggerFactory.getLogger(CommandService.class);

    @Transactional
    public void control(CommandDTO commandDTO) {
        //check if the device exists with the user given
        boolean isDeviceExist = validateDevice(commandDTO.getDevice(), commandDTO.getUser());
        try{
            if (!isDeviceExist) {
                logger.error("Device Belong to the User Cannot be Found");
                throw new DeviceNotFoundException();
            }
            sendCommand(commandDTO);
        } catch (Exception e) {
            logger.error("Device and User Could Not be Confirmed", e);
            throw new DeviceNotAuthorizedException();
        }
    }

    public void sendCommand(CommandDTO commandData) {
        try{
            logger.info("Command Sent for Execution");
            kafkaTemplate.send(TOPIC, commandData);
        } catch (Exception e){
            logger.error("Command Could Not be Sent", e);
        }
    }


    //validating with external service
    private boolean validateDevice(String deviceId, String userId) {
        logger.info("Device and User Confirmation Started");
        try {
            ResponseEntity<String> response = deviceClient.validateDevice(deviceId, userId);
            //device exists with the user
            logger.info("Device and User Confirmed");
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            //device does not exist with the user
            return false;
        }
    }
}
