package com.example.backend.controller;

import com.example.backend.dto.CalcRequest;
import com.example.backend.dto.CalcResponse;
import com.example.backend.service.CalcService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CalcController {
    private final CalcService calcService;

    public CalcController(CalcService calcService) {
        this.calcService = calcService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<CalcResponse> calculate(@Valid @RequestBody CalcRequest request) {
        CalcResponse response = calcService.calculate(request);
        return ResponseEntity.ok(response);
    }
}