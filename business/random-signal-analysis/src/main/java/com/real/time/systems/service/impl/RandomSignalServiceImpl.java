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

        List<Harmonic> harmonics = IntStream.rangeClosed(1, amountOfHarmonic).mapToObj(h -> Harmonic.builder()
                .amplitude(ThreadLocalRandom.current().nextDouble(0.1, 100.))
                .phase(ThreadLocalRandom.current().nextDouble(0, 360))
                .build()).collect(toList());

        List<Point> points = IntStream.rangeClosed(0, pointsAmount)
                .mapToObj(time -> Point.builder().time(time).value(harmonics.stream()
                .mapToDouble(v -> v.getAmplitude() * Math.sin(frequency * time + v.getPhase())).sum()).build())
                .collect(toList());

        double mathExpectation = points.stream()
                .mapToDouble(Point::getValue).sum() / (pointsAmount + 1);
        double dispersion = points.stream()
                .mapToDouble(p -> Math.pow(mathExpectation - p.getValue(), 2)).sum() / pointsAmount;

        return RandomSignalResponseDto.builder()
                .points(points).mathematicalExpectation(mathExpectation).dispersion(dispersion).build();
    }

//    public static void main(String[] args) {
//
//        List<Harmonic> harmonics = IntStream.rangeClosed(0, 4).mapToObj(h -> Harmonic.builder()
//                .amplitude(h)
//                .phase(h + 1).build()).collect(toList());
//        System.out.println("harmonics : " + harmonics);
//
//        List<Point> points = IntStream.rangeClosed(0, 6).mapToObj(time -> {
//            double sum = harmonics.stream()
//                    .mapToDouble(v -> v.getAmplitude() * v.getPhase() + time).sum();
//            System.out.println("sum : " + sum);
//            return Point.builder().time(time).value(sum).build();
//        }).collect(toList());
//        System.out.println("points : " + points);
//    }
}
