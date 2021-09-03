package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseOKDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.*;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountService;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.*;
import com.ddangnmarket.ddangmarkgetbackend.login.SessionConst;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;
    private final AccountService accountService;

    private final String[] STATUS_PARAMS = {"new", "complete", "reserve"};
    private final String GET_ALL_POST_PARAM_KEY = "status";

//    @PostMapping("/newV1")
//    public PostResponseDto postV1(@RequestBody PostRequestDto postRequestDto, @ApiIgnore HttpSession session){
//
//        Account account = getSessionCheckedAccount(session);
//
//        Post post = new Post(postRequestDto.getTitle(), postRequestDto.getDesc(),
//                postRequestDto.getPrice(), postRequestDto.getCategoryTag(), account);
//
//        Long postId = postService.post(post);
//
//        return new PostResponseDto(postId);

//    }
    @PostMapping
    public ResponseEntity<ResponseOKDto<PostResponseDto>> post(@RequestBody PostRequestDto postRequestDto, @ApiIgnore HttpSession session){

        Account account = getSessionCheckedAccount(session);

        Long postId = postService.post(postRequestDto.getTitle(), postRequestDto.getDesc(),
                postRequestDto.getPrice(), postRequestDto.getCategoryTag(), account);

        return new ResponseEntity<>(new ResponseOKDto<>(new PostResponseDto(postId)), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ResponseOKDto<GetPostResponseDto>> getPost(@PathVariable Long postId, @ApiIgnore HttpSession session){
        getSessionCheckedAccount(session);

        Post post = postService.findById(postId);

        return new ResponseEntity<>(new ResponseOKDto<>(new GetPostResponseDto(post)), HttpStatus.OK);
    }

    @PutMapping("/{postId}/reserve/{chatId}")
    public ResponseEntity<ResponseOKDto<String>> reservePost(@PathVariable Long postId, @PathVariable Long chatId,
                            @ApiIgnore HttpSession session){
        Account seller = getSessionCheckedAccount(session);

        validateIsSellerPost(postId, seller);

        postService.changeReserve(postId, chatId);
        return new ResponseEntity<>(new ResponseOKDto<>(""), HttpStatus.OK);
    }

    @PutMapping("/{postId}/reserve/cancel")
    public ResponseEntity<ResponseOKDto<String>> cancelReservePost(@PathVariable Long postId, @ApiIgnore HttpSession session){
        Account seller = getSessionCheckedAccount(session);

        validateIsSellerPost(postId, seller);

        postService.cancelReserve(postId);
        return new ResponseEntity<>(new ResponseOKDto<>(""), HttpStatus.OK);
    }

    private void validateIsSellerPost(Long postId, Account seller) {
        seller.getPosts().stream()
                .filter(post-> post.getId().equals(postId))
                .findAny().orElseThrow(()->
                        new IllegalArgumentException("해당 사용자의 게시글이 아닙니다."));
    }

//    @GetMapping
//    public GetAllPostResponseDto getAllPost(@ApiIgnore HttpSession session){
//
//        getSessionCheckedAccount(session);
//
//        List<Post> posts = postService.findAll();
//        return new GetAllPostResponseDto(posts);

//    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "new", required = false ,dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "reserve", required = false ,dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "complete", required = false ,dataTypeClass = String.class, paramType = "query")
    })
    @GetMapping
    public ResponseEntity<ResponseOKDto<GetAllPostResponseDto>> getAllPost(
            @ApiIgnore @RequestParam(required = false) @Nullable MultiValueMap<String, String> statuses,
            @ApiIgnore HttpSession session){

        getSessionCheckedAccount(session);

        if (statuses.size() == 0){
            List<Post> posts = postService.findAll();
            return new ResponseEntity<>(new ResponseOKDto<>(new GetAllPostResponseDto(posts)), HttpStatus.OK);
        }

        statusRequestParamKeyAndValueValidation(statuses);

        List<PostStatus> postStatuses = statuses.get(GET_ALL_POST_PARAM_KEY)
                .stream().map(String::toUpperCase)
                .map(PostStatus::valueOf)
                .collect(toList());

        List<Post> posts = postService.findAllByStatuses(postStatuses);
        return new ResponseEntity<>(new ResponseOKDto<>(new GetAllPostResponseDto(posts)), HttpStatus.OK);

    }

    @GetMapping("/seller")
    public ResponseEntity<ResponseOKDto<GetAllPostResponseDto>> getAllPostBySeller(
            @RequestParam(required = false) @Nullable String status,
            @ApiIgnore HttpSession session) {

        Account account = getSessionCheckedAccount(session);

        if (status == null){
            List<Post> posts = postService.findPostAllBySeller(account);
            return new ResponseEntity<>(new ResponseOKDto<>(new GetAllPostResponseDto(posts)), HttpStatus.OK);
        }

        statusRequestParamValueValidation(status);

        List<Post> posts = postService.findPostAllBySellerAndStatus(account,
                PostStatus.valueOf(status.toUpperCase()));
        return new ResponseEntity<>(new ResponseOKDto<>(new GetAllPostResponseDto(posts)), HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<ResponseOKDto<GetAllPostResponseDto>> getAllPostByCategory(
            @RequestParam(name = "category") String categoryTag,
            @RequestParam(name = "status", required = false) @Nullable String status,
            @ApiIgnore HttpSession session){
        getSessionCheckedAccount(session);

        if(status == null){
            // 예외 메세지 처리 필요
            // IllegalArgumentException
            List<Post> posts = postService.findPostAllByCategory(CategoryTag.valueOf(categoryTag.toUpperCase()));
            return new ResponseEntity<>(new ResponseOKDto<>(new GetAllPostResponseDto(posts)), HttpStatus.OK);
        }
        statusRequestParamValueValidation(status);
        List<Post> posts = postService.findAllByCategoryAndStatus(
                CategoryTag.valueOf(categoryTag.toUpperCase()),
                PostStatus.valueOf(status.toUpperCase()));

        return new ResponseEntity<>(new ResponseOKDto<>(new GetAllPostResponseDto(posts)), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseOKDto<String>> deletePost(@PathVariable Long postId, @ApiIgnore HttpSession session){
        getSessionCheckedAccount(session);

        postService.deleteById(postId);
        return new ResponseEntity<>(new ResponseOKDto<>("게시글이 삭제되었습니다."), HttpStatus.OK);
    }

    private Account getSessionCheckedAccount(HttpSession session) {
        Long accountId = (Long) session.getAttribute(SessionConst.LOGIN_ACCOUNT);
        return accountService.findAccount(accountId);
    }

    private void statusRequestParamKeyAndValueValidation(MultiValueMap<String, String> statuses) {
        boolean keyMatch = !statuses.keySet().stream().allMatch(GET_ALL_POST_PARAM_KEY::equals);
        boolean statusMatch = !Arrays.asList(STATUS_PARAMS).containsAll(statuses.get(GET_ALL_POST_PARAM_KEY));

        if(keyMatch || statusMatch){
            throw new IllegalArgumentException("잘못된 경로 입니다.");
        }
    }

    private void statusRequestParamValueValidation(String status) {
        boolean statusMatch = Arrays.asList(STATUS_PARAMS).contains(status);
        if(!statusMatch){
            throw new IllegalArgumentException("잘못된 경로 입니다.");
        }
    }

}
