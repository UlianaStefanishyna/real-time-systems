package com.real.time.systems.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RandomSignalResponseDto {
    private List<Point> points;
}