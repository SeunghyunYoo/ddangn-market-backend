package com.ddangnmarket.ddangmarkgetbackend.domain.image;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageController {

    @PostMapping
    public void uploadImage(@RequestPart("image") MultipartFile file, HttpSession session) throws IOException {
        if(!file.isEmpty()){
            String path = session.getServletContext().getRealPath("/") + "images/" + file.getOriginalFilename();
            file.transferTo(new File(path));
        }

    }

}
