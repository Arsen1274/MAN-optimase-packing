package com.example.manpacking.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Arsen Sirkovych on 06.01.2024
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class EvaluationResult {

    private Integer productA;
    private Integer productB;
    private Integer productC;

    private Integer packingA;
    private Integer packingB;
    private Integer packingC;

    private Integer amountOfProducts;

}
