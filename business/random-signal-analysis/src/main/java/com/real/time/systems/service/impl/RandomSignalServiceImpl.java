package com.real.time.systems.service.impl;

import com.real.time.systems.model.Harmonic;
import com.real.time.systems.model.Point;
import com.real.time.systems.model.RandomSignalResponseDto;
import com.real.time.systems.service.RandomSignalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.stream.*;

import static java.util.stream.Collectors.*;

@Slf4j
@Service
public class RandomSignalServiceImpl implements RandomSignalService {

    @Override
    public RandomSignalResponseDto calculate(int amountOfHarmonic, int pointsAmount, double frequency) {

        double diff = frequency / amountOfHarmonic;
        List<Harmonic> harmonics = IntStream.rangeClosed(1, amountOfHarmonic).mapToObj(h -> Harmonic.builder()
                .amplitude(ThreadLocalRandom.current().nextDouble(0, 1))
                .phase(ThreadLocalRandom.current().nextDouble(0, 360))
                .frequency(diff * h)
                .build()).collect(toList());

        List<Double> rgb = new ArrayList<>();
        List<Point> points = IntStream.rangeClosed(0, pointsAmount)
                .mapToObj(time -> Point.builder().time(time).value(harmonics.stream()
                        .mapToDouble(v -> {
                            double sum = v.getAmplitude() * Math.sin(v.getFrequency() * time + v.getPhase());
                            rgb.add(Math.abs(sum * 255));
                            return sum;
                        }).average().orElseThrow()).build())
                .collect(toList());

        double mathExpectation = points.stream().mapToDouble(Point::getValue).sum() / (pointsAmount + 1);
        double dispersion = points.stream()
                .mapToDouble(p -> Math.pow(mathExpectation - p.getValue(), 2)).sum() / pointsAmount;

        return RandomSignalResponseDto.builder()
                .points(points).mathematicalExpectation(mathExpectation).dispersion(dispersion)
                .rgb("rgb(" + Math.round(rgb.get(0)) + "," + Math.round(rgb.get(1)) + "," + Math.round(rgb.get(2)) + ")")
                .build();
    }
}