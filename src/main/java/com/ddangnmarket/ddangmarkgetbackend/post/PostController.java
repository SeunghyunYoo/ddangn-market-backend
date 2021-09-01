package com.ddangnmarket.ddangmarkgetbackend.post;

import com.ddangnmarket.ddangmarkgetbackend.account.AccountJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.account.AccountService;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.login.SessionConst;
import com.ddangnmarket.ddangmarkgetbackend.post.dto.*;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;
    private final AccountJpaRepository accountJpaRepository;

    @PostMapping("/new")
    public PostResponseDto post(@RequestBody PostRequestDto postRequestDto, @ApiIgnore HttpSession session){

        Account account = getSessionCheckedAccount(session);

        Post post = new Post(postRequestDto.getTitle(), postRequestDto.getDesc(),
                postRequestDto.getPrice(), postRequestDto.getCategoryTag(), account);

        Long postId = postService.post(post);

        return new PostResponseDto(postId);
    }

    @GetMapping
    public GetAllPostResponseDto getAllPost(@ApiIgnore HttpSession session){
        getSessionCheckedAccount(session);

        List<Post> posts = postService.findAll();

        return new GetAllPostResponseDto(posts);
    }

    @GetMapping("/{postId}")
    public GetPostResponseDto getPost(@PathVariable Long postId, @ApiIgnore HttpSession session){
        getSessionCheckedAccount(session);

        Post post = postService.findById(postId);

        return new GetPostResponseDto(post);
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable Long postId, @ApiIgnore HttpSession session){
        getSessionCheckedAccount(session);

        postService.deleteById(postId);
        return "게시글이 삭제되었습니다.";
    }

    private Account getSessionCheckedAccount(HttpSession session) {
        Long accountId = (Long) session.getAttribute(SessionConst.LOGIN_ACCOUNT);

        Account account = accountJpaRepository.findById(accountId).orElseThrow(() ->
                new IllegalStateException("존재하지 않는 회원입니다."));
        return account;
    }

}
