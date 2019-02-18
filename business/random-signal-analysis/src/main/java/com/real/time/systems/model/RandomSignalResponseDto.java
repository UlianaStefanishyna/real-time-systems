package com.real.time.systems.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class RandomSignalResponseDto {
    private List<DiscretePoint> timeDiscretePoints;
}