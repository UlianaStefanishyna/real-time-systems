package com.real.time.systems.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RandomSignalResponseDto {
//    private List<Point> pointsX;
//    private List<Point> pointsY;
//    private List<Point> pointsXAutoCorr;
//    private List<Point> pointsYAutoCorr;
//    private List<Point> pointsXYCorr;

    private List<Point> rzzAuto;
    private List<Point> rxyCorr;

//    private double mathematicalExpectationX;
//    private double mathematicalExpectationY;
//    private double dispersionX;
//    private double dispersionY;
//    private String rgbX;
//    private String rgbY;
}