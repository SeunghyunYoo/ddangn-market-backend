package com.ddangnmarket.ddangmarkgetbackend.domain.interest;

import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseOKDto;
import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseSimpleOKDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Interest;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountService;
import com.ddangnmarket.ddangmarkgetbackend.domain.interest.dto.AddInterestRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.interest.dto.AddInterestResponseDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.interest.dto.GetAllInterestResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/interests")
public class InterestController {

    private final InterestService interestService;
    private final AccountService accountService;

    @PostMapping("/new")
    public ResponseEntity<ResponseOKDto<AddInterestResponseDto>> addInterest(@RequestBody AddInterestRequestDto addInterestRequestDto, @ApiIgnore HttpSession session){
        Account account = accountService.checkSessionAndFindAccount(session);

        Long interestId = interestService.addInterest(account, addInterestRequestDto.getPostId());

        return new ResponseEntity<>(new ResponseOKDto<>(
                new AddInterestResponseDto(interestId)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseOKDto<GetAllInterestResponseDto>> getAllInterest(@ApiIgnore HttpSession session){
        Account account = accountService.checkSessionAndFindAccount(session);

        List<Interest> interests = interestService.findAllInterests(account);

        return new ResponseEntity<>(new ResponseOKDto<>(
                new GetAllInterestResponseDto(interests)), HttpStatus.OK);
    }

    @DeleteMapping("/{interestId}")
    public ResponseEntity<ResponseSimpleOKDto> deleteInterest(@PathVariable Long interestId, @ApiIgnore HttpSession session){
        Account account = accountService.checkSessionAndFindAccount(session);

        interestService.deleteInterest(account, interestId);

        return new ResponseEntity<>(new ResponseSimpleOKDto(), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ResponseSimpleOKDto> deleteInterestByPostId(@PathVariable Long postId, @ApiIgnore HttpSession session){
        Account account = accountService.checkSessionAndFindAccount(session);

        interestService.deleteInterestByPostId(account, postId);

        return new ResponseEntity<>(new ResponseSimpleOKDto(), HttpStatus.OK);
    }
}
