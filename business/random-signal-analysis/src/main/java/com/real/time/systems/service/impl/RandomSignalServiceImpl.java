package com.real.time.systems.service.impl;

import com.real.time.systems.model.DiscretePoint;
import com.real.time.systems.model.Harmonic;
import com.real.time.systems.model.RandomSignalResponseDto;
import com.real.time.systems.service.RandomSignalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.*;

@Slf4j
@Service
public class RandomSignalServiceImpl implements RandomSignalService {

    @Override
    public RandomSignalResponseDto calculate(int amountOfHarmonic, long pointsAmount, double frequency) {
        List<Harmonic> harmonics = new ArrayList<>();

        return RandomSignalResponseDto.builder().timeDiscretePoints(
                DoubleStream.iterate(1, i -> i + 1).limit(pointsAmount).boxed()
                .collect(Collectors.toList()).stream()
                .collect(Collectors.toMap(p -> p, p -> DiscretePoint.builder()
                        .value(DoubleStream.iterate(1, i -> i + 1).limit(amountOfHarmonic).boxed()
                                .collect(Collectors.toList()).stream()
                                .mapToDouble(y -> {
                                    double amplitude = ThreadLocalRandom.current().nextDouble(0.1, Double.MAX_VALUE);
                                    double phase = ThreadLocalRandom.current().nextDouble(0.1, 360);
                                    double v = amplitude * Math.sin(frequency * p * phase);
                                    harmonics.add(Harmonic.builder()
                                            .amplitude(amplitude).phase(phase).timePoint(p).build());
                                    return v;
                                }).sum()).harmonics(harmonics).build()))).build();
    }

//    public static void main(String[] args) {
//
//        System.out.println(collect1);
//        List<Harmonic> harmonics = new ArrayList<>();
//        List<DiscretePoint> discretePoints = new ArrayList<>();
//
//        for (int t = 0; t < pointsAmount; t++) {
//            double xi = 0;
//            harmonics.clear();
//            for (int j = 0; j < amountOfHarmonic; j++) {
//                double amplitude = ThreadLocalRandom.current().nextDouble(0.1, Double.MAX_VALUE);
//                double phase = ThreadLocalRandom.current().nextDouble(0., 360);
//                xi += amplitude * Math.sin(frequency * t * phase);
//                harmonics.add(Harmonic.builder().amplitude(amplitude).phase(phase).timePoint(t).build());
//            }
//            discretePoints.add(DiscretePoint.builder().harmonics(harmonics).value(xi).build());
//        }
//        return RandomSignalResponseDto.builder().timeDiscretePoints(discretePoints).build();
//    }
}
