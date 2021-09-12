package com.ddangnmarket.ddangmarkgetbackend.domain.file;

import com.ddangnmarket.ddangmarkgetbackend.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {
//    @Value("${image.dir}")
    private final String imageDir = "images/";

    public String getFullPath(HttpSession session, String filename){
        if (filename == null){
            throw new IllegalArgumentException();
        }
        return getBaseDir(session) + imageDir + filename;
    }

    public List<UploadFile> storeFiles(HttpSession session, List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()){
                UploadFile uploadFile = storeFile(session, multipartFile);
                storeFileResult.add(uploadFile);
            }
        }
        return  storeFileResult;
    }

    public UploadFile storeFile(HttpSession session, MultipartFile multipartFile) throws IOException {
        if(multipartFile == null || multipartFile.isEmpty()){
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(session, storeFileName)));
        return new UploadFile(originalFilename, storeFileName);
    }

    private String getBaseDir(HttpSession session){
        return session.getServletContext().getRealPath("/");
    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

}
