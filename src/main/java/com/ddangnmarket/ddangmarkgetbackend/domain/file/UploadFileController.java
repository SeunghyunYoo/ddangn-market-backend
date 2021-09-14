package com.ddangnmarket.ddangmarkgetbackend.domain.file;

import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseOKDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountService;
import com.ddangnmarket.ddangmarkgetbackend.domain.file.dto.UploadFileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.util.List;

/**
 * @author SeunghyunYoo
 */
@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class UploadFileController {

    private final AccountService accountService;
    private final UploadFileService uploadFileService;

    @PostMapping
    public ResponseEntity<ResponseOKDto<UploadFileResponseDto>> uploadImage(@RequestParam List<MultipartFile> files, @ApiIgnore HttpSession session) {
        accountService.checkSessionAndFindAccount(session);

        List<Long> fileIds = uploadFileService.uploadFiles(files);

        return new ResponseEntity<>(new ResponseOKDto<>(new UploadFileResponseDto(fileIds)), HttpStatus.OK);
    }

    @GetMapping("{imageId}")
    public Resource downloadImage(@PathVariable Long imageId, @ApiIgnore HttpSession session) throws MalformedURLException {
        // file: <- 이게 붙으면 내부파일에 직접 접근
        return uploadFileService.downloadImage(imageId);
    }


}















