package com.real.time.systems.modelmapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entity {

    private int value;
    private String str;
    private Nested nested;

}
