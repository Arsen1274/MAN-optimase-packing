package com.example.manpacking.entity.cylinder;

import lombok.*;

/**
 * @author Arsen Sirkovych on 08.01.2024
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CylinderOptimizationObject implements Comparable<CylinderOptimizationObject> {
    private Integer n;  //d
    private Integer p;  //h
    private Double amount;
    private Double diameter;
    private Double height;
    private Double correlation;


    @Override
    public int compareTo(CylinderOptimizationObject o) {
        return Double.compare(this.getCorrelation(), o.getCorrelation());
    }
}
