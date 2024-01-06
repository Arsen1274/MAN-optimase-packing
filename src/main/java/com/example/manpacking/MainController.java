package com.example.manpacking;

import com.example.manpacking.entity.EvaluateFormObject;
import com.example.manpacking.entity.EvaluationResult;
import com.example.manpacking.service.EvaluationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Arsen Sirkovych on 06.01.2024
 */

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {
    private final EvaluationService evaluationService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getHomePage(){
        log.info("GET /home");
        return "home";
    }

    @GetMapping("/evaluate-form")
    public String getEvaluateForm(Model model){
        model.addAttribute( "evaluateFormObject", new EvaluateFormObject());
        return "evaluate-form";
    }


    @PostMapping ("/process-evaluate")
    public String getProcessEvaluateForm(EvaluateFormObject evaluateFormObject, Model model){
        EvaluationResult evaluationResult = evaluationService.evaluateResult(evaluateFormObject);
        model.addAttribute( "evaluationResult", evaluationResult);
        return "evaluation-result";
    }


    @GetMapping(value = "/para", produces = MediaType.IMAGE_PNG_VALUE)
    public void getParallelepiped(HttpServletResponse response) throws IOException {
        // Set content type to image/png
        response.setContentType(MediaType.IMAGE_PNG_VALUE);

        // Generate the parallelepiped image
        BufferedImage image = evaluationService.generateParallelepipedImage();

        // Send the image to the client
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
        os.close();
    }



}
