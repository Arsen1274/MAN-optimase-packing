package com.example.manpacking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Arsen Sirkovych on 06.01.2024
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluateFormObject {
    private ShapeEnum shape;
    private Integer length;
    private Integer width;
    private Integer height;
}
