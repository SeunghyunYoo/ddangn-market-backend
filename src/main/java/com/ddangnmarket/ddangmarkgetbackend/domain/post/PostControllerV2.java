package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseOKDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountService;
import com.ddangnmarket.ddangmarkgetbackend.domain.file.UploadFileStore;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.PostRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.PostResponseDto;
import com.fasterxml.jackson.databind.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/posts")
public class PostControllerV2 {
    private final PostService postService;
    private final AccountService accountService;
    private final ObjectMapper objectMapper;
    private final UploadFileStore uploadFileStore;

    @PostMapping
    public ResponseEntity<ResponseOKDto<PostResponseDto>> post(
            @RequestBody @Validated PostRequestDto postRequestDto,
            @ApiIgnore HttpSession session) {

        Account account = accountService.checkSessionAndFindAccountWithActivityArea(session);

        Long postId = postService.post(postRequestDto.getTitle(), postRequestDto.getDesc(), postRequestDto.getPrice(),
                postRequestDto.getCategoryTag(), postRequestDto.getFileIds(), account);

        return new ResponseEntity<>(new ResponseOKDto<>(
                new PostResponseDto(postId)), HttpStatus.OK);
    }

}
