package com.ddangnmarket.ddangmarkgetbackend.domain.file;

import com.ddangnmarket.ddangmarkgetbackend.domain.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * @author SeunghyunYoo
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UploadFileService {

    private final UploadFileRepository uploadFileRepository;
    private final UploadFileStore uploadFileStore;

    public List<Long> uploadFiles(List<MultipartFile> files) {
        List<UploadFile> uploadFiles = uploadFileStore.storeFiles(files);

        uploadFileRepository.saveAll(uploadFiles);
        return uploadFiles.stream().map(UploadFile::getId).collect(toList());
    }

    public Long uploadFile(MultipartFile file){
        // TODO 단일 값 예외처리 필요
        UploadFile uploadFile = uploadFileStore.storeFile(file);
        uploadFileRepository.save(uploadFile);
        return 0L;
    }

    public Resource downloadImage(Long imageId) throws MalformedURLException {
        UploadFile uploadFile = uploadFileRepository.findById(imageId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 이미지 입니다."));
        String storeFileName = uploadFile.getStoreFileName();
        return new UrlResource("file:" + uploadFileStore.getFullPath(storeFileName));
    }
}









