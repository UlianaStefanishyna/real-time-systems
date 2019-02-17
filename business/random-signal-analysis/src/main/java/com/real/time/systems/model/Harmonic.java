package com.real.time.systems.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Harmonic {
    private double amplitude;
    private double phase;
    private double timePoint;
}
