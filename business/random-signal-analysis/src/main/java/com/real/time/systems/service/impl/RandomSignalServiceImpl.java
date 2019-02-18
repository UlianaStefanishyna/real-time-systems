package com.real.time.systems.service.impl;

import com.real.time.systems.model.DiscretePoint;
import com.real.time.systems.model.RandomSignalResponseDto;
import com.real.time.systems.service.RandomSignalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.stream.*;

@Slf4j
@Service
public class RandomSignalServiceImpl implements RandomSignalService {
    @Override
    public RandomSignalResponseDto calculate(int amountOfHarmonic, int pointsAmount, double frequency) {
        return RandomSignalResponseDto.builder()
                .timeDiscretePoints(IntStream.rangeClosed(1, pointsAmount).boxed().map(time -> DiscretePoint.builder()
                        .value(IntStream.rangeClosed(1, amountOfHarmonic)
                                .mapToDouble(p -> calculateHarmonic.apply(frequency, time)).sum())
                        .timePoint(time).build()).collect(Collectors.toList())).build();
    }

    private BiFunction<Double, Integer, Double> calculateHarmonic = (frequency, time) -> {
        double amplitude = ThreadLocalRandom.current().nextDouble(0.1, Double.MAX_VALUE);
        double phase = ThreadLocalRandom.current().nextDouble(0.1, 360);
        return amplitude * Math.sin(frequency * time * phase);
    };

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
