package com.jeanbarcellos.processmanager.controllers;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    @GetMapping("")
    public ResponseEntity<HashMap<String, Object>> showStatus() {
        var response = new HashMap<String, Object>();

        response.put("service", "Proccess Manager API");
        response.put("status", "up");
        response.put("httpStatus", HttpStatus.OK.value());

        return ResponseEntity.ok(response);
    }

}
