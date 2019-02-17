package com.real.time.systems.service.impl;

import com.real.time.systems.model.RandomSignalResponseDto;
import com.real.time.systems.service.RandomSignalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RandomSignalServiceImpl implements RandomSignalService {

    @Override
    public RandomSignalResponseDto calculate(int amountOfHarmonic, long pointsAmount, double frequency) {
        return null;
    }
}
