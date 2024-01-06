package com.example.manpacking.entity;

import lombok.Getter;

/**
 * @author Arsen Sirkovych on 06.01.2024
 */

//@AllArgsConstructor
@Getter
public enum ShapeEnum {
    CYLINDER("cylinder"),
    RECTANGLE("rectangle");

    private final String shapeForm;

    ShapeEnum(String shapeForm) {
        this.shapeForm = shapeForm;
    }
}
