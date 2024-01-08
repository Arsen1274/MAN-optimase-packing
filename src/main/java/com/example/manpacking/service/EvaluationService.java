package com.example.manpacking.service;

import com.example.manpacking.entity.EvaluateFormObject;
import com.example.manpacking.entity.EvaluationResult;
import com.example.manpacking.entity.cylinder.CylinderOptimizationObject;
import com.example.manpacking.entity.cylinder.EvaluateCylinderFormObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Arsen Sirkovych on 06.01.2024
 */

@Slf4j
@Service
public class EvaluationService {

    Integer ITERATION_NUM = 50;
    Integer LIMIT_NUM = 15;
    Integer BEFORE_NUM_CHECK = 5;


    public EvaluationResult evaluateResult(EvaluateFormObject formObj) {
        Integer koefK = evaluateKoefK(formObj.getLength(), formObj.getHeight());
        Integer koefN = evaluateKoefN(formObj.getLength(), formObj.getWidth());

        Integer packingA = formObj.getLength();
        Integer packingB = formObj.getWidth() * koefN;
        Integer packingC = formObj.getHeight() * koefK;

        Integer amount = amountOfProducts(koefK, koefN);

        return EvaluationResult.builder()
                .productA(formObj.getLength())
                .productB(formObj.getWidth())
                .productC(formObj.getHeight())
                .packingA(packingA)
                .packingB(packingB)
                .packingC(packingC)
                .amountOfProducts(amount)
                .build();
    }

    private Integer amountOfProducts(Integer koefK, Integer koefN) {
        return koefN * koefK;
    }

    private Integer evaluateKoefN(Integer length, Integer width) {
        float correlation = length / (float) width;
        if (correlation >= 2f) {
            return Math.round(correlation);
        } else {
            return 1;
        }
    }

    private Integer evaluateKoefK(Integer length, Integer height) {
        float koef = length / (float) height;
        return Math.round(koef);
    }

    private static Integer doubleToInt(Double d) {
        return Math.toIntExact(Math.round(d));
    }

    public BufferedImage generateParallelepipedImage() {
        int width = 300;
        int height = 200;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();

        // Set the background color
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // Set the parallelepiped color
        g.setColor(Color.BLACK);

        // Draw the parallelepiped using lines
        g.drawLine(50, 50, 250, 50);  // Top front
        g.drawLine(250, 50, 300, 100); // Top right
        g.drawLine(300, 100, 100, 100); // Top back
        g.drawLine(100, 100, 50, 50); // Top left

        g.drawLine(50, 50, 50, 150); // Left front
        g.drawLine(250, 50, 250, 150); // Right front
        g.drawLine(300, 100, 300, 200); // Right back
        g.drawLine(100, 100, 100, 200); // Left back

        g.drawLine(50, 150, 250, 150); // Bottom front
        g.drawLine(250, 150, 300, 200); // Bottom right
        g.drawLine(300, 200, 100, 200); // Bottom back
        g.drawLine(100, 200, 50, 150); // Bottom left

        g.dispose();
        return image;
    }


    public List<CylinderOptimizationObject> evaluateCylinderPacking(EvaluateCylinderFormObject formObj) {
        Integer multiplyKoef = 7;
        Integer RADIUS_KOEF = 6;
        Integer sevenUnitDiam = RADIUS_KOEF * formObj.getDiameter();


//        Integer packingRadius;
//        Integer packingDiam;
//        Integer packingHeight;
//        Integer amountOfProducts;
        LinkedList<CylinderOptimizationObject> corrMap = new LinkedList<>();
        if (formObj.getDiameter() < formObj.getHeight()) {

            for (int n = 1; n < ITERATION_NUM; n++) {
                for (int p = 1; p < ITERATION_NUM; p++) {
                    Double tempHeight = (double) formObj.getHeight() * p;
                    Double tempDiameter = formObj.getDiameter() * Math.pow(3, n);
                    Double correlation = tempHeight / (double) tempDiameter;
                    Double tempAmount = Math.pow(7, n) * p;

                    if (p > BEFORE_NUM_CHECK && biggerThanPreviousCorr(correlation, corrMap)) {
                        break;
                    }

                    CylinderOptimizationObject object = CylinderOptimizationObject.builder()
                            .diameter(tempDiameter)
                            .height(tempHeight)
                            .n(n)
                            .p(p)
                            .amount(tempAmount)
                            .correlation(correlation)
                            .build();
                    corrMap.add(object);
                }
            }


        } else {

            for (int n = 1; n < ITERATION_NUM; n++) {
                for (int p = 1; p < ITERATION_NUM; p++) {
                    Double tempHeight = (double) formObj.getHeight() * p;
                    Double tempDiameter = formObj.getDiameter() * Math.pow(3, n);
                    Double correlation =  (double) tempDiameter/ tempHeight;
                    Double tempAmount = Math.pow(7, n) * p;

                    if (p > BEFORE_NUM_CHECK && biggerThanPreviousCorr(correlation, corrMap)) {
                        break;
                    }

                    CylinderOptimizationObject object = CylinderOptimizationObject.builder()
                            .diameter(tempDiameter)
                            .height(tempHeight)
                            .n(n)
                            .p(p)
                            .amount(tempAmount)
                            .correlation(correlation)
                            .build();
                    corrMap.add(object);
                }
            }

        }

        List<CylinderOptimizationObject> sortedMap = corrMap.stream().sorted(new Comparator<CylinderOptimizationObject>() {
                    @Override
                    public int compare(CylinderOptimizationObject o1, CylinderOptimizationObject o2) {
                        Double o1Corr = Math.abs(o1.getCorrelation() - 1);
                        Double o2Corr = Math.abs(o2.getCorrelation() - 1);
                        return o1Corr.compareTo(o2Corr);
                    }
                })
                .filter(e -> e.getCorrelation() != 0.0)
                .limit(LIMIT_NUM)
                .collect(Collectors.toList());
        Collections.sort(corrMap);
        return sortedMap;


//        Integer koefK = evaluateKoefK(sevenUnitDiam, formObj.getHeight());
//        packingRadius = sevenUnitDiam * koefK / 2;
//        packingDiam = sevenUnitDiam * koefK;
//        packingHeight = formObj.getHeight();
//        amountOfProducts = multiplyKoef * koefK;
//        return CylinderResponse.builder()
//                .radius(formObj.getRadius())
//                .height(formObj.getHeight())
//                .packingRadius(packingRadius)
//                .packingDiam(packingDiam)
//                .packingHeight(packingHeight)
//                .amountOfProducts(amountOfProducts).build();
    }

    private boolean biggerThanPreviousCorr(Double correlation, LinkedList<CylinderOptimizationObject> corrMap) {
        if (corrMap.size() > BEFORE_NUM_CHECK) {

//            for (int i = corrMap.size() -1 ; i > corrMap.size() - num; i--) {
            if (correlation > corrMap.get(corrMap.size() - 1).getCorrelation()) {
                if (correlation > corrMap.get(corrMap.size() - 2).getCorrelation()) {
                    if (correlation > corrMap.get(corrMap.size() - 3).getCorrelation()) {
                        if (correlation > corrMap.get(corrMap.size() - 4).getCorrelation()) {
                            if (correlation > corrMap.get(corrMap.size() - 5).getCorrelation()) {
//                                if (correlation > corrMap.get(corrMap.size() - 6).getCorrelation()) {
//                                    if (correlation > corrMap.get(corrMap.size() - 7).getCorrelation()) {
                                return true;
//                                    }
//                                }
                            }
                        }
                    }
                }
            }
//            }
        }

        return false;
    }
}
