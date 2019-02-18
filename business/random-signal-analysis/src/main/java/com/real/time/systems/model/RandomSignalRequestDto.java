package com.real.time.systems.model;

import lombok.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RandomSignalRequestDto {

    @NotNull
    private int amountOfHarmonic;

    @NotNull
    private int pointsAmount;

    @NotNull
    private double frequency;
}
