package com.real.time.systems.modelmapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Nested {
    private Long nestedLong;
    private ThirdLevel thirdLevel;
}
