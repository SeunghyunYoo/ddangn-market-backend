package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseOKDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.UploadFile;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountService;
import com.ddangnmarket.ddangmarkgetbackend.domain.file.FileStore;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.PostRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.PostResponseDto;
import com.ddangnmarket.ddangmarkgetbackend.login.SessionConst;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBase;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import javax.validation.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/posts")
public class PostControllerV2 {
    private final PostService postService;
    private final AccountService accountService;
    private final ObjectMapper objectMapper;
    private final FileStore fileStore;

    @PostMapping
//    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseOKDto<PostResponseDto>> post(
            @RequestPart("data") String data, @RequestPart(value = "file", required = false) MultipartFile file,
            @ApiIgnore HttpSession session) throws IOException {

        Account account = getSessionCheckedAccount(session);

        PostRequestDto postRequestDto = objectMapper.readValue(data, PostRequestDto.class);

        Long postId = postService.post(postRequestDto.getTitle(), postRequestDto.getDesc(),
                postRequestDto.getPrice(), postRequestDto.getCategoryTag(), account, file, session);

        return new ResponseEntity<>(new ResponseOKDto<>(
                new PostResponseDto(postId)), HttpStatus.OK);
    }

    @GetMapping("{postId}/images")
    public Resource downloadImage(@PathVariable Long postId, @ApiIgnore HttpSession session) throws MalformedURLException {
        // file: <- 이게 붙으면 내부파일에 직접 접근
        Post post = postService.findPost(postId);
        String storeFileName = post.getUploadFile().getStoreFileName();
        return new UrlResource("file:" + fileStore.getFullPath(session, storeFileName));
    }

    private Account getSessionCheckedAccount(HttpSession session) {
        Long accountId = (Long) session.getAttribute(SessionConst.LOGIN_ACCOUNT);
        return accountService.findAccountWithActivityAreas(accountId);
    }

}
