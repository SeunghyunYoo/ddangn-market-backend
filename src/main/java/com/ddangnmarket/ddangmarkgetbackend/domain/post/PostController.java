package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseOKDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.*;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountService;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.*;
import com.ddangnmarket.ddangmarkgetbackend.login.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.List;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;
    private final AccountService accountService;

    private final List<String> STATUS_PARAMS = List.of("new", "complete", "reserve");

    /**
     * 게시글 등록
     * @param postRequestDto
     * @param session
     * @return
     */
    @PostMapping
    public ResponseEntity<ResponseOKDto<PostResponseDto>> post(@RequestBody PostRequestDto postRequestDto, @ApiIgnore HttpSession session){

        Account account = getSessionCheckedAccount(session);

        Long postId = postService.post(postRequestDto.getTitle(), postRequestDto.getDesc(),
                postRequestDto.getPrice(), postRequestDto.getCategoryTag(), account);

        return new ResponseEntity<>(new ResponseOKDto<>(new PostResponseDto(postId)), HttpStatus.OK);
    }

    /**
     * 게시글 조회
     * @param postId
     * @param session
     * @return
     */
    @GetMapping("/{postId}")
    public ResponseEntity<ResponseOKDto<GetPostResponseDto>> getPost(@PathVariable Long postId, @ApiIgnore HttpSession session){
        getSessionCheckedAccount(session);

        Post post = postService.findById(postId);

        return new ResponseEntity<>(new ResponseOKDto<>(new GetPostResponseDto(post)), HttpStatus.OK);
    }

    /**
     * 게시글 수정
     * @param postId
     * @param updatePostRequestDto
     * @param session
     * @return
     */
    @PutMapping("/{postId}")
    public ResponseEntity<ResponseOKDto<String>> updatePost(@PathVariable Long postId,
                @RequestBody UpdatePostRequestDto updatePostRequestDto, @ApiIgnore HttpSession session){
        getSessionCheckedAccount(session);

        postService.updatePost(postId, updatePostRequestDto);
        return new ResponseEntity<>(new ResponseOKDto<>("SUCCEEDED"), HttpStatus.OK);
    }

    /**
     * 판매자가 해당 게시글 예약 지정
     * @param postId
     * @param chatId
     * @param session
     * @return
     */
    @PutMapping("/{postId}/reserve/{chatId}")
    public ResponseEntity<ResponseOKDto<String>> reservePost(@PathVariable Long postId, @PathVariable Long chatId,
                            @ApiIgnore HttpSession session){
        Account seller = getSessionCheckedAccount(session);

        validateIsSellerPost(postId, seller);

        postService.changeReserve(postId, chatId);
        return new ResponseEntity<>(new ResponseOKDto<>(""), HttpStatus.OK);
    }

    /**
     * 예약 취소
     * @param postId
     * @param session
     * @return
     */
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

    /**
     * 상품 상태별 게시글 전체 조회
     * @param status
     * @param session
     * @return
     */
    @GetMapping
    public ResponseEntity<ResponseOKDto<GetAllPostResponseDto>> getAllPost(
            @RequestParam(required = false) @Nullable List<String> status,
            @ApiIgnore HttpSession session){

        getSessionCheckedAccount(session);

        if (status == null || status.size() == 0){
            List<Post> posts = postService.findAll();
            return new ResponseEntity<>(new ResponseOKDto<>(new GetAllPostResponseDto(posts)), HttpStatus.OK);
        }

        getPostAllParamListValidation(status);

        List<PostStatus> postStatuses = convertStringToPostStatus(status);

        List<Post> posts = postService.findAllByStatuses(postStatuses);
        return new ResponseEntity<>(new ResponseOKDto<>(new GetAllPostResponseDto(posts)), HttpStatus.OK);

    }

    /**
     * 판매자가 등록한 게시글 조회 + 상태별 조회
     * @param status
     * @param session
     * @return
     */
    @GetMapping("/seller")
    public ResponseEntity<ResponseOKDto<GetAllPostResponseDto>> getAllPostBySeller(
            @RequestParam(required = false) @Nullable List<String> status,
            @ApiIgnore HttpSession session) {

        Account account = getSessionCheckedAccount(session);

        if (status == null){
            List<Post> posts = postService.findPostAllBySeller(account);
            return new ResponseEntity<>(new ResponseOKDto<>(new GetAllPostResponseDto(posts)), HttpStatus.OK);
        }

        getPostAllParamListValidation(status);

        List<PostStatus> postStatuses = convertStringToPostStatus(status);

        List<Post> posts = postService.findPostAllBySellerAndStatuses(account, postStatuses);
        return new ResponseEntity<>(new ResponseOKDto<>(new GetAllPostResponseDto(posts)), HttpStatus.OK);
    }

    /**
     * 카테고리별 전체 상품 조회
     * @param categoryTag
     * @param status
     * @param session
     * @return
     */
    @GetMapping("/category")
    public ResponseEntity<ResponseOKDto<GetAllPostResponseDto>> getAllPostByCategory(
            @RequestParam(name = "category") String categoryTag,
            @RequestParam(name = "status", required = false) @Nullable List<String> status,
            @ApiIgnore HttpSession session){
        getSessionCheckedAccount(session);

        if(status == null){
            // 예외 메세지 처리 필요
            // IllegalArgumentException
            List<Post> posts = postService.findPostAllByCategory(CategoryTag.valueOf(categoryTag.toUpperCase()));
            return new ResponseEntity<>(new ResponseOKDto<>(new GetAllPostResponseDto(posts)), HttpStatus.OK);
        }
        getPostAllParamListValidation(status);

        List<PostStatus> postStatuses = convertStringToPostStatus(status);

        List<Post> posts = postService.findAllByCategoryAndStatuses(
                CategoryTag.valueOf(categoryTag.toUpperCase()),
                postStatuses);

        return new ResponseEntity<>(new ResponseOKDto<>(new GetAllPostResponseDto(posts)), HttpStatus.OK);
    }


//    @GetMapping("/address")

    public void getAllPostByAddress(){
        // TODO
    }
//    @GetMapping("/interest")

    public void getAllPostByInterest(){
        //TODO
    }
//    @GetMapping("")

    public void getAllPostByPurchase(){
        //TODO
    }
    /**
     * 게시글 삭제
     * @param postId
     * @param session
     * @return
     */
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

    private void getPostAllParamListValidation(List<String> status) {
        boolean statusMatch = status.stream().noneMatch(STATUS_PARAMS::contains);
        if(statusMatch){
            throw new IllegalArgumentException("잘못된 경로 입니다.");
        }
    }

    private void getPostAllParamValidation(String status) {
        boolean statusMatch = STATUS_PARAMS.contains(status);
        if(!statusMatch){
            throw new IllegalArgumentException("잘못된 경로 입니다.");
        }
    }

    private List<PostStatus> convertStringToPostStatus(List<String> status) {
        List<PostStatus> postStatuses = status
                .stream().map(String::toUpperCase)
                .map(PostStatus::valueOf)
                .collect(toList());
        return postStatuses;
    }

}
