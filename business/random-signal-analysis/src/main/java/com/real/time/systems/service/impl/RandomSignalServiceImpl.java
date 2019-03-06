package com.real.time.systems.service.impl;

import com.real.time.systems.model.Harmonic;
import com.real.time.systems.model.Point;
import com.real.time.systems.model.RandomSignalResponseDto;
import com.real.time.systems.service.RandomSignalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.*;

import static java.util.stream.Collectors.*;

@Slf4j
@Service
public class RandomSignalServiceImpl implements RandomSignalService {

    @Override
    public RandomSignalResponseDto calculate(int amountOfHarmonic, int pointsAmount, double frequency) {
        System.out.println("in function");
        List<Double> rgbX = new ArrayList<>();
        List<Double> rgbY = new ArrayList<>();
        List<Double> rgbZ = new ArrayList<>();
        List<Point> rxy = new ArrayList<>();
        List<Point> rzz = new ArrayList<>();

        for (int i = 0; i < 10_000; i += 1000) {
            long start = new Date().getTime();
            List<Harmonic> harmonicsX = this.generateHarmonics(frequency / amountOfHarmonic, amountOfHarmonic);
            List<Harmonic> harmonicsY = this.generateHarmonics(frequency / amountOfHarmonic, amountOfHarmonic);
            List<Point> pointsX = this.calculatePoints(i, harmonicsX, rgbX);
            List<Point> pointsY = this.calculatePoints(i, harmonicsY, rgbY);
            double mathExpectationX = this.calculateMathExpectation(pointsX, i);
            double mathExpectationY = this.calculateMathExpectation(pointsY, i);
            List<Point> correlation = this.calculateCorrelation(mathExpectationX, mathExpectationY, pointsX, pointsY);
            long finish = new Date().getTime();
            rxy.add(Point.builder()
                    .value((finish - start) / 1000.0)
                    .time(i).build());
        }

        for (int i = 0; i < 10_000; i += 1000) {
            long start = new Date().getTime();
            List<Harmonic> harmonicsZ = this.generateHarmonics(frequency / amountOfHarmonic, amountOfHarmonic);
            List<Point> pointsZ = this.calculatePoints(i, harmonicsZ, rgbZ);
            double mathExpectationZ = this.calculateMathExpectation(pointsZ, i);
            List<Point> autoCorrelationZ = this.calculateAutoCorrelation(mathExpectationZ, pointsZ);
            long finish = new Date().getTime();
            rzz.add(Point.builder()
                    .value((finish - start) / 1000.0)
                    .time(i).build());
        }

//        double dispersionX = this.calculateDispersion(pointsX, mathExpectationX, pointsAmount);
//        double dispersionY = this.calculateDispersion(pointsY, mathExpectationY, pointsAmount);
//        double dispersionZ = this.calculateDispersion(pointsZ, mathExpectationZ, pointsAmount);

//        List<Point> autoCorrelationX = this.calculateAutoCorrelation(mathExpectationX, pointsX);
//        List<Point> autoCorrelationY = this.calculateAutoCorrelation(mathExpectationY, pointsY);

        return RandomSignalResponseDto.builder()
                .rxyCorr(rxy)
                .rzzAuto(rzz)
//                .pointsX(pointsX)
//                .pointsY(pointsY)
//                .pointsXAutoCorr(autoCorrelationX)
//                .pointsYAutoCorr(autoCorrelationY)
//                .pointsXYCorr(correlation)
//                .rgbX("rgb(" + Math.round(rgbX.get(0)) + "," + Math.round(rgbX.get(1)) + "," + Math.round(rgbX.get(2)) + ")")
//                .rgbY("rgb(" + Math.round(rgbY.get(0)) + "," + Math.round(rgbY.get(1)) + "," + Math.round(rgbY.get(2)) + ")")
//                .mathematicalExpectationX(mathExpectationX)
//                .mathematicalExpectationY(mathExpectationY)
//                .dispersionX(dispersionX)
//                .dispersionY(dispersionY)
                .build();
    }

    @SuppressWarnings("Duplicates")
    private List<Point> calculateAutoCorrelation(double mathExpectation, List<Point> points) {
        return IntStream.range(0, points.size())
                .mapToObj(tau -> Point.builder().time(tau).value(IntStream.range(0, points.size())
                        .mapToDouble(time -> (points.get(time).getValue() - mathExpectation) *
                                (points.get(time + tau >= points.size() - 1 ? points.size() - 1 : time + tau)
                                        .getValue() - mathExpectation))
                        .sum() / (points.size() / 2.0 + 1)).build())// / ((points.size() / 2.0) + 1)
                .collect(toList());
    }

    @SuppressWarnings("Duplicates")
    private List<Point> calculateCorrelation(double mathExpectationX, double mathExpectationY,
                                             List<Point> pointsX, List<Point> pointsY) {
        return IntStream.range(0, pointsX.size() / 2)
                .mapToObj(tau -> Point.builder().time(tau).value(IntStream.range(0, pointsX.size() / 2)
                        .mapToDouble(time -> (pointsX.get(time).getValue() - mathExpectationX) *
                                (pointsY.get(time + tau)
                                        .getValue() - mathExpectationY))
                        .sum() / (pointsX.size() / 2.0 + 1)).build())
                .collect(toList());
    }

    private List<Harmonic> generateHarmonics(double frequency, int amountOfHarmonic) {
        return IntStream.rangeClosed(1, amountOfHarmonic)
                .mapToObj(h -> Harmonic.builder()
                        .amplitude(ThreadLocalRandom.current().nextDouble(0, 1))
                        .phase(ThreadLocalRandom.current().nextDouble(0, 360))
                        .frequency(frequency * h).build())
                .collect(toList());
    }

    private List<Point> calculatePoints(int pointsAmount, List<Harmonic> harmonics, List<Double> rgb) {
        return IntStream.rangeClosed(0, pointsAmount)
                .mapToObj(time -> Point.builder().time(time).value(harmonics.stream()
                        .mapToDouble(v -> {
                            double sum = v.getAmplitude() * Math.sin(v.getFrequency() * time + v.getPhase());
                            rgb.add(Math.abs(sum * 255));
                            return sum;
                        }).average().orElseThrow()).build())
                .collect(toList());
    }

    private double calculateMathExpectation(List<Point> points, int pointsAmount) {
        return points.stream().mapToDouble(Point::getValue).sum() / (pointsAmount + 1);
    }

    private double calculateDispersion(List<Point> points, double mathExpectation, int pointsAmount) {
        return points.stream().mapToDouble(p -> Math.pow(mathExpectation - p.getValue(), 2)).sum() / pointsAmount;
    }
}