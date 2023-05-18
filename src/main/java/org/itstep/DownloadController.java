package org.itstep;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class DownloadController {

    @GetMapping(value = "/download")
    public String list(Model model) {
        File dir = new File("src/main/resources/uploaded/");
        model.addAttribute("files", getFiles(dir.getAbsolutePath()));
        return "list";
    }

    @GetMapping(value = "/download", params = {"file"})
    @ResponseBody
    public ResponseEntity<?> download(@RequestParam("file") String fileName, HttpServletResponse response) {
        File dir = new File("src/main/resources/uploaded/");
        byte[] fileBytes = null;
        try {
            fileBytes = Files.readAllBytes(Paths.get(dir.getAbsolutePath() + "/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(fileBytes);
    }

    public Set<String> getFiles(String dir) {
        return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }
}
