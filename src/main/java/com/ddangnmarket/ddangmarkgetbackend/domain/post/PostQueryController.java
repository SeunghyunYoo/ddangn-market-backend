package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseOKDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountService;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.GetAllPostResponseDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.GetPostOneResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author SeunghyunYoo
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostQueryController {

    private final PostService postService;
    private final AccountService accountService;

    private final List<String> STATUS_PARAMS = List.of("new", "complete", "reserve");

    @GetMapping
    public ResponseEntity<ResponseOKDto<GetAllPostResponseDto>> getAllPosts(
            @RequestParam(required = false) @Nullable List<String> status,
            @ApiIgnore HttpSession session){

        Account account = accountService.checkSessionAndFindAccountWithActivityArea(session);

        List<Post> posts = checkStatusParamAndFindPosts(account, status);

        return new ResponseEntity<>(new ResponseOKDto<>(new GetAllPostResponseDto(posts)), HttpStatus.OK);

    }

    private List<Post> checkStatusParamAndFindPosts(Account account, List<String> status){
        if (status == null || status.size() == 0){
            return postService.findAll(account);
        }
        postStatusParamValidation(status);
        List<PostStatus> postStatuses = convertStringToPostStatus(status);
        return postService.findAllByStatuses(account, postStatuses);
    }

    private void postStatusParamValidation(List<String> status) {
        if(status.stream().noneMatch(STATUS_PARAMS::contains)){
            throw new IllegalArgumentException("잘못된 경로 입니다.");
        }
    }

    private List<PostStatus> convertStringToPostStatus(List<String> status) {
        return status.stream()
                .map(PostStatus::fromString)
                .collect(toList());
    }


    @GetMapping("/sales")
    public ResponseEntity<ResponseOKDto<GetAllPostResponseDto>> getAllSellerPosts(
            @RequestParam(required = false) @Nullable List<String> status,
            @ApiIgnore HttpSession session) {

        Account account = accountService.checkSessionAndFindAccountWithActivityArea(session);

        List<Post> posts = checkStatusParamAndFindSellerPosts(account, status);
        return new ResponseEntity<>(new ResponseOKDto<>(new GetAllPostResponseDto(posts)), HttpStatus.OK);
    }

    private List<Post> checkStatusParamAndFindSellerPosts(Account seller, List<String> status){
        if (status == null || status.size() == 0){
            return postService.findPostAllBySeller(seller);
        }
        postStatusParamValidation(status);
        List<PostStatus> postStatuses = convertStringToPostStatus(status);
        return postService.findPostAllBySellerAndStatuses(seller, postStatuses);
    }


    @GetMapping("/category")
    public ResponseEntity<ResponseOKDto<GetAllPostResponseDto>> getAllPostsByCategory(
            @RequestParam(name = "category") String categoryTag,
            @RequestParam(name = "status", required = false) @Nullable List<String> status,
            @ApiIgnore HttpSession session){
        Account account = accountService.checkSessionAndFindAccountWithActivityArea(session);

        List<Post> posts = checkStatusParamAndFindPostsByCategory(account, categoryTag, status);

        return new ResponseEntity<>(new ResponseOKDto<>(new GetAllPostResponseDto(posts)), HttpStatus.OK);
    }

    private List<Post> checkStatusParamAndFindPostsByCategory(Account account, String categoryTag, List<String> status){
        if (status == null || status.size() == 0){
            return postService.findPostAllByCategory(account, CategoryTag.fromString(categoryTag));
        }
        postStatusParamValidation(status);
        List<PostStatus> postStatuses = convertStringToPostStatus(status);
        return postService.findAllByCategoryAndStatuses(account,
                CategoryTag.valueOf(categoryTag.toUpperCase()),
                postStatuses);
    }

}
