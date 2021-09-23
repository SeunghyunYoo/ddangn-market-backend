package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseOKDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountService;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.GetAllPostResponseDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.GetPagePostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author SeunghyunYoo
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/posts")
public class PostPageQueryController {

    private final PostService postService;
    private final PostPageService postPageService;

    private final AccountService accountService;

    private final List<String> STATUS_PARAMS = List.of("new", "complete", "reserve");

    @GetMapping
    public ResponseEntity<ResponseOKDto<GetPagePostsResponseDto>> getPagePosts(
            @RequestParam(required = false) @Nullable List<String> status,
            @RequestParam int page, @RequestParam int size,
            @ApiIgnore HttpSession session){

        Account account = accountService.checkSessionAndFindAccountWithActivityArea(session);

        Page<Post> pagePost = checkStatusParamAndFindPosts(account, status, page, size);

        return new ResponseEntity<>(new ResponseOKDto<>(new GetPagePostsResponseDto(pagePost)), HttpStatus.OK);

    }

    private Page<Post> checkStatusParamAndFindPosts(Account account, List<String> status, int page, int size){
        if (status == null || status.size() == 0){
            return postPageService.findPagePosts(account, page, size);
        }
        postStatusParamValidation(status);
        List<PostStatus> postStatuses = convertStringToPostStatus(status);
        return postPageService.findPagePostsByStatuses(account, postStatuses, page, size);
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
    public ResponseEntity<ResponseOKDto<GetPagePostsResponseDto>> getPageSellerPosts(
            @RequestParam(required = false) @Nullable List<String> status,
            @RequestParam int page, @RequestParam int size,
            @ApiIgnore HttpSession session) {

        Account account = accountService.checkSessionAndFindAccountWithActivityArea(session);

        Page<Post> pagePost = checkStatusParamAndFindSellerPosts(account, status, page, size);
        return new ResponseEntity<>(new ResponseOKDto<>(new GetPagePostsResponseDto(pagePost)), HttpStatus.OK);
    }

    private Page<Post> checkStatusParamAndFindSellerPosts(
            Account seller, List<String> status, int page, int size){
        if (status == null || status.size() == 0){
            return postPageService.findPagePostsBySeller(seller, page, size);
        }
        postStatusParamValidation(status);
        List<PostStatus> postStatuses = convertStringToPostStatus(status);
        return postPageService.findPagePostsBySellerAndStatuses(seller, postStatuses, page, size);
    }


    @GetMapping("/category")
    public ResponseEntity<ResponseOKDto<GetPagePostsResponseDto>> getPagePostsByCategory(
            @RequestParam(name = "category") String categoryTag,
            @RequestParam(name = "status", required = false) @Nullable List<String> status,
            @RequestParam int page, @RequestParam int size,
            @ApiIgnore HttpSession session){
        Account account = accountService.checkSessionAndFindAccountWithActivityArea(session);

        Page<Post> pagePost = checkStatusParamAndFindPostsByCategory(
                account, categoryTag, status, page, size);

        return new ResponseEntity<>(new ResponseOKDto<>(new GetPagePostsResponseDto(pagePost)), HttpStatus.OK);
    }

    private Page<Post> checkStatusParamAndFindPostsByCategory(
            Account account, String categoryTag, List<String> status,
            int page, int size){
        if (status == null || status.size() == 0){
            return postPageService.findPagePostsByCategory(
                    account, CategoryTag.fromString(categoryTag), page, size);
        }
        postStatusParamValidation(status);
        List<PostStatus> postStatuses = convertStringToPostStatus(status);
        return postPageService.findPagePostsByCategoryAndStatuses(
                account, CategoryTag.valueOf(categoryTag.toUpperCase()),
                postStatuses, page, size);
    }

}
