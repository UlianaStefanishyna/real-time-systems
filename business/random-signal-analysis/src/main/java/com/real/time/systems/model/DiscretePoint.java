package com.real.time.systems.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DiscretePoint {
    private List<Harmonic> harmonics;
    private double value;
}
