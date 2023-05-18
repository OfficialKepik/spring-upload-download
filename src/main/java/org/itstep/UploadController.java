package org.itstep;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
public class UploadController {

    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }

    @PostMapping
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        String fileName = file.getOriginalFilename();
        //System.out.println(fileName);

        File dir = new File("src/main/resources/uploaded/");
        try {
            file.transferTo(new File(dir.getAbsolutePath() + "/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "index";
    }
}
