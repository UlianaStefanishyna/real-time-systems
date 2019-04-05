package com.real.time.systems.modelmapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dto {

    private int value;
    private String str;
    private String nestedThirdLevelName;
    private Long nestedNestedLong;
}
