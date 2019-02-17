package com.real.time.systems.service;

import com.real.time.systems.model.RandomSignalResponseDto;

public interface RandomSignalService {

    RandomSignalResponseDto calculate(int amountOfHarmonic, long pointsAmount, double frequency);
}