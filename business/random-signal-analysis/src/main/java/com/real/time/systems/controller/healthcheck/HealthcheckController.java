package com.real.time.systems.controller.healthcheck;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthcheckController {

    @RequestMapping(value = "/api/healthcheck", method = RequestMethod.GET)
    public ResponseEntity<?> checkHealth(){
        return ResponseEntity.ok("service is alive");
    }
}