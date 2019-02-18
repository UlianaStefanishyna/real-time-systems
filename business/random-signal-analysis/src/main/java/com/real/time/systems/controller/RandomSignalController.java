package com.real.time.systems.controller;

import com.real.time.systems.model.RandomSignalRequestDto;
import com.real.time.systems.model.RandomSignalResponseDto;
import com.real.time.systems.service.RandomSignalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/chart")
public class RandomSignalController {

    private final RandomSignalService randomSignalService;

    public RandomSignalController(RandomSignalService randomSignalService) {
        this.randomSignalService = randomSignalService;
    }

    @PostMapping
    public ResponseEntity<?> draw(@RequestBody @Valid RandomSignalRequestDto request){
        log.info("request got {}", request);
        RandomSignalResponseDto calculate = this.randomSignalService
                .calculate(request.getAmountOfHarmonic(), request.getPointsAmount(), request.getFrequency());
//        log.info("calculate={}", calculate);
        return ResponseEntity.ok(calculate);
    }
}