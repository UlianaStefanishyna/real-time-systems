package com.real.time.systems.modelmapper;

import org.modelmapper.ModelMapper;

public class ModelMapperExample {

    public static void main(String[] args) {
        ModelMapper modelMapper = new ModelMapper();
        Dto res = modelMapper.map(new Entity(1, "str", new Nested(8L, new ThirdLevel("name"))), Dto.class);
        System.out.println(res);
    }
}
