package com.real.time.systems.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiscretePoint {
    private int timePoint;
    private double value;
}
