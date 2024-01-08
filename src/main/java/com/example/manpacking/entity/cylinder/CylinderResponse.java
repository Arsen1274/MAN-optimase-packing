package com.example.manpacking.entity.cylinder;

import lombok.*;

/**
 * @author Arsen Sirkovych on 08.01.2024
 */

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CylinderResponse {

    private Integer radius;
    private Integer height;

    private Integer packingRadius;
    private Integer packingDiam;
    private Integer packingHeight;

    private Integer amountOfProducts;
}
