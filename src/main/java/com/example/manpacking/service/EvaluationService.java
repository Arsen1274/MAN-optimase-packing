package com.example.manpacking.service;

import com.example.manpacking.entity.EvaluateFormObject;
import com.example.manpacking.entity.EvaluationResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Arsen Sirkovych on 06.01.2024
 */

@Slf4j
@Service
public class EvaluationService {

    public EvaluationResult evaluateResult(EvaluateFormObject formObj) {
        Double koefK = evaluateKoefK(formObj.getLength(), formObj.getWidth(), formObj.getHeight());
        Integer koefN = evaluateKoefN(formObj.getLength(), formObj.getWidth());

        Integer packingA = formObj.getLength();
        Integer packingB = formObj.getWidth() * koefN;
        Integer packingC = Math.toIntExact(Math.round(formObj.getHeight() * koefK));

        return EvaluationResult.builder()
                .productA(formObj.getLength())
                .productB(formObj.getWidth())
                .productC(formObj.getHeight())
                .packingA(packingA)
                .packingB(packingB)
                .packingC(packingC)
                .build();
    }

    private Integer evaluateKoefN(Integer length, Integer width) {
        float correlation = length / (float) width;
        if(correlation >= 2f ){
            return Math.round(correlation);
        }else{
            return 1;
        }
    }

    private Double evaluateKoefK(Integer length, Integer width, Integer height) {
        float koef = (length * width) / (float) height;
        return Math.ceil(koef); //ceil - round up
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


}
