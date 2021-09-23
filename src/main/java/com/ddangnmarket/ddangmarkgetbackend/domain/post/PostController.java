package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseOKDto;
import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseSimpleOKDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.*;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountService;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;
    private final AccountService accountService;

    private final List<String> STATUS_PARAMS = List.of("new", "complete", "reserve");

    @PostMapping
    public ResponseEntity<ResponseOKDto<PostResponseDto>> post(
            @RequestBody @Validated PostRequestDto postRequestDto,
            @ApiIgnore HttpSession session) {

        Account account = accountService.checkSessionAndFindAccountWithActivityArea(session);

        Long postId = postService.post(account, postRequestDto.getTitle(), postRequestDto.getDesc(), postRequestDto.getPrice(),
                postRequestDto.getCategoryTag(), postRequestDto.getFileIds());

        return new ResponseEntity<>(new ResponseOKDto<>(
                new PostResponseDto(postId)), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ResponseOKDto<GetPostOneResponseDto>> getPost(
            @PathVariable Long postId, @ApiIgnore HttpSession session){
        accountService.checkSessionAndFindAccountWithActivityArea(session);

        Post post = postService.findPostAndAddViewCount(postId);

        return new ResponseEntity<>(new ResponseOKDto<>(
                new GetPostOneResponseDto(post)), HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ResponseSimpleOKDto> updatePost(
            @PathVariable Long postId,
            @RequestBody UpdatePostRequestDto updatePostRequestDto, @ApiIgnore HttpSession session){
        Account seller = accountService.checkSessionAndFindAccountWithActivityArea(session);

        postService.updatePost(seller, postId, updatePostRequestDto);
        return new ResponseEntity<>(new ResponseSimpleOKDto(), HttpStatus.OK);
    }

    @PostMapping("/{postId}/sales/{chatId}")
    public ResponseEntity<ResponseSimpleOKDto> salePost(
            @PathVariable Long postId, @PathVariable Long chatId, @ApiIgnore HttpSession session){
        Account seller = accountService.checkSessionAndFindAccountWithActivityArea(session);

        postService.sale(seller, postId, chatId);
        return new ResponseEntity<>(new ResponseSimpleOKDto(), HttpStatus.OK);
    }

    @PostMapping("/{postId}/sales/cancel")
    public ResponseEntity<ResponseSimpleOKDto> cancelSalePost(
            @PathVariable Long postId, @ApiIgnore HttpSession session){
        Account seller = accountService.checkSessionAndFindAccountWithActivityArea(session);

        // TODO chatId를 받아올지, 전체 chat을 looping해서 상태를 바꿀지
        postService.deletePurchaseAndSale(seller, postId);
        return new ResponseEntity<>(new ResponseSimpleOKDto(), HttpStatus.OK);
    }

    @PostMapping("/{postId}/reserve/{chatId}")
    public ResponseEntity<ResponseSimpleOKDto> reservePost(@PathVariable Long postId, @PathVariable Long chatId,
                            @ApiIgnore HttpSession session){
        Account seller = accountService.checkSessionAndFindAccountWithActivityArea(session);
        
        // TODO 여기서 1대다 (seller -> posts) 쿼리 너무 많이 날라감
        // sale purchase 테이블을 조회하는데, orphan removal 영향인지 check 필요

        postService.changeReserve(seller, postId, chatId);
        return new ResponseEntity<>(new ResponseSimpleOKDto(), HttpStatus.OK);
    }

    @PostMapping("/{postId}/reserve/cancel")
    public ResponseEntity<ResponseSimpleOKDto> cancelReservePost(@PathVariable Long postId, @ApiIgnore HttpSession session){
        Account seller = accountService.checkSessionAndFindAccountWithActivityArea(session);

        postService.cancelReserve(seller, postId);
        return new ResponseEntity<>(new ResponseSimpleOKDto(), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseSimpleOKDto> deletePost(@PathVariable Long postId, @ApiIgnore HttpSession session){
        Account seller = accountService.checkSessionAndFindAccountWithActivityArea(session);
        // TODO 거래 완료된 게시글은 삭제 못하도록 변경
//        postService.deleteById(postId);
        postService.delete(seller, postId);
        return new ResponseEntity<>(new ResponseSimpleOKDto(), HttpStatus.OK);
    }

}
