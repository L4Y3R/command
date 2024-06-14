package com.demo.command.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//Feign client for interacting with different services
@FeignClient(name = "demonstration")
public interface DeviceClient {

    //validating if a device exists with the given user
    @GetMapping("/api/v1/devices/conf/")
    ResponseEntity<String> validateDevice(@RequestParam("deviceId") String deviceId,
                                          @RequestParam("userId") String userId);
}
