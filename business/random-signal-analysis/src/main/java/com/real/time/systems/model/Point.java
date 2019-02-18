package com.real.time.systems.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Point {
    private int time;
    private double value;
}
