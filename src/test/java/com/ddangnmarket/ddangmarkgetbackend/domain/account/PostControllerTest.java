package com.ddangnmarket.ddangmarkgetbackend.domain.account;

import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseOKDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.ChatStatus;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.dto.ActivityAreaRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.dto.SignUpRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.chat.dto.CreateChatRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.chat.dto.GetAllChatResponseDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong;
import com.ddangnmarket.ddangmarkgetbackend.domain.interest.dto.AddInterestRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.interest.dto.GetAllInterestResponseDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostService;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.GetAllPostResponseDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.PostRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.login.dto.LoginRequestDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong.GUMI;
import static com.ddangnmarket.ddangmarkgetbackend.domain.post.PostStatus.RESERVE;
import static com.ddangnmarket.ddangmarkgetbackend.login.SessionConst.LOGIN_ACCOUNT;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@Transactional
@Slf4j
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

//    @Autowired AccountController accountController;

    @Autowired
    PostService postService;
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

//    protected MockHttpSession session;

    @Before
    public void init(){
//        this.mockMvc = MockMvcBuilders.standaloneSetup(accountController)
//                .setControllerAdvice(new ExControllerAdvice())
//                .build();
    }


    @Test
    void signUp() throws Exception {
        // Login
        SignUpRequestDto requestDto = new SignUpRequestDto(
                "soobin", "010-0000-0000", "soobin@gmail.com", "00000000", GUMI);

        String request = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/api/v1/accounts/new").content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk());

        MockHttpSession session = loginAndGetSession("soobin@gmail.com", "00000000");

        //== ????????? == //

        // ????????? ??????
        PostRequestDto postRequestDto = new PostRequestDto("title", "desc", 1000, CategoryTag.OTHERS, List.of());
        MockPost(session, "/api/v1/posts", postRequestDto);

        // ?????? ????????? ??????
        System.out.println("=============");
        System.out.println("?????? ????????? ??????");
        ResponseOKDto<GetAllPostResponseDto> allPostResult = MockGetPosts(session, "/api/v1/posts");
        assertThat(allPostResult.getData().getPosts().size()).isEqualTo(4+1);
        Long postId1 = allPostResult.getData().getPosts().get(0).getPostId();
        Long postId2 = allPostResult.getData().getPosts().get(2).getPostId();

        // ???????????? ?????? ????????? ??????
        System.out.println("=============");
        System.out.println("???????????? ?????? ????????? ??????");
        ResponseOKDto<GetAllPostResponseDto> allPostBySellerResult = MockGetPosts(session, "/api/v1/posts/sales");
        assertThat(allPostBySellerResult.getData().getPosts().size()).isEqualTo(1);

        // ??????????????? ?????? ??????
        System.out.println("=============");
        System.out.println("??????????????? ????????? ??????");
        ResponseOKDto<GetAllPostResponseDto> allPostByCategory =
                MockGetPosts(session, "/api/v1/posts/category?category=others");
        assertThat(allPostByCategory.getData().getPosts().size()).isEqualTo(1);

        // ?????? ?????? ??????
        System.out.println("=============");
        System.out.println("?????? ?????? ??????");
        ActivityAreaRequestDto activityAreaRequestDto =
                new ActivityAreaRequestDto(Dong.YATAP3, 0);
        MockPost(session, "/api/v1/accounts/activity-area", activityAreaRequestDto);

        // ?????? ????????? ?????? ?????? ???????????? ?????? ???????????? ??????
        ResponseOKDto<GetAllPostResponseDto> allPostResultYATAP = MockGetPosts(session, "/api/v1/posts");
        assertThat(allPostResultYATAP.getData().getPosts().size()).isEqualTo(0);

        //== ???????????? ==//

        // ???????????? ??????
        System.out.println("=============");
        System.out.println("?????? ????????? ??????");
        AddInterestRequestDto addInterestRequestDto = new AddInterestRequestDto(postId1);
        MockPost(session, "/api/v1/interests/new", addInterestRequestDto);

        // ???????????? ??????
        System.out.println("=============");
        System.out.println("?????? ?????? ????????? ??????");
        ResponseOKDto<GetAllInterestResponseDto> allInterests =
                MockGetInterests(session, "/api/v1/interests");
        Long interestId = allInterests.getData().getInterests().get(0).getId();
        assertThat(allInterests.getData().getInterests().size()).isEqualTo(1);
        assertThat(allInterests.getData().getInterests()
                .get(0).getPost().getDong()).isEqualTo(GUMI);

        // ???????????? ??????
        MockDelete(session, "/api/v1/interests/" + interestId.toString());

        ResponseOKDto<GetAllInterestResponseDto> allInterestsAfterDelete =
                MockGetInterests(session, "/api/v1/interests");
        assertThat(allInterestsAfterDelete.getData().getInterests().size()).isEqualTo(0);

        //== ?????? ?????? ==//
        CreateChatRequestDto createChatRequestDto = new CreateChatRequestDto(postId2);
        MockPost(session, "/api/v1/chats", createChatRequestDto);

        // ?????? ??????
        session = loginAndGetSession("account2@gmail.com", "00000000");
        MockPost(session, "/api/v1/chats", createChatRequestDto);

        session = loginAndGetSession("account3@gmail.com", "00000000");
        MockPost(session, "/api/v1/chats", createChatRequestDto);

        // ???????????? ????????? (account1)
        session = loginAndGetSession("account1@gmail.com", "00000000");

        // ?????? ??? ??????
        ResponseOKDto<GetAllChatResponseDto> allChats
                = MockGetChats(session, "/api/v1/chats/sales?postId=" + postId2.toString());
        assertThat(allChats.getData().getChats().size()).isEqualTo(3);
        Long chatId1 = allChats.getData().getChats().get(0).getChatId();
        Long chatId2 = allChats.getData().getChats().get(1).getChatId();
        Long chatId3 = allChats.getData().getChats().get(2).getChatId();


        //== ?????? ?????? ==//
        // ?????? ?????? ??????
        MockPost(session, "/api/v1/posts/" + postId2 +"/reserve/" + chatId1.toString());
        ResponseOKDto<GetAllPostResponseDto> allReserved1 =
                MockGetPosts(session, "/api/v1/posts/sales?status=reserve");

        assertThat(allReserved1.getData().getPosts().get(0).getStatus()).isEqualTo(RESERVE);
        GetAllChatResponseDto.GetChatResponseDto reservedChat1 = MockGetChats(session, "/api/v1/chats/sales?postId=" + postId2)
                .getData().getChats().get(0);
        assertThat(reservedChat1.getChatStatus()).isEqualTo(ChatStatus.RESERVE);

        // ?????? ?????? ??????
        MockPost(session, "/api/v1/posts/" + postId2 +"/reserve/" + chatId2.toString());
        ResponseOKDto<GetAllPostResponseDto> allReserved2 =
                MockGetPosts(session, "/api/v1/posts/sales?status=reserve");

        assertThat(allReserved2.getData().getPosts().get(0).getStatus()).isEqualTo(RESERVE);
        reservedChat1 = MockGetChats(session, "/api/v1/chats/sales?postId=" + postId2)
                .getData().getChats().get(0);
        assertThat(reservedChat1.getChatStatus()).isEqualTo(ChatStatus.NONE);
        GetAllChatResponseDto.GetChatResponseDto reservedChat2 = MockGetChats(session, "/api/v1/chats/sales?postId=" + postId2)
                .getData().getChats().get(1);
        assertThat(reservedChat2.getChatStatus()).isEqualTo(ChatStatus.RESERVE);

        // ?????? ?????? ?????? /api/v1/posts/{postId}/sales/cancel

        MockPost(session, "/api/v1/posts/" + postId2 +"/reserve/cancel");
        reservedChat2 = MockGetChats(session, "/api/v1/chats/sales?postId=" + postId2)
                .getData().getChats().get(1);
        assertThat(reservedChat2.getChatStatus()).isEqualTo(ChatStatus.NONE);
        // ?????? ??????
        MockPost(session, "/api/v1/posts/" + postId2 +"/reserve/" + chatId2);


        //== ?????? ?????? ==//

        // ?????? ?????? (?????? -> ??????)
        MockPost(session, "/api/v1/posts/" + postId2 +"/sales/" + chatId1);

        // ?????? ?????????
        // ?????? ?????????
        // ?????? ??????

        // ?????? ??????

        // ?????? -> ??????

    }

    private MockHttpSession loginAndGetSession(String mail, String password) throws Exception {
        LoginRequestDto loginRequestDto = new LoginRequestDto(mail, password);
        mockMvc.perform(post("/api/v1/login")
                        .content(objectMapper.writeValueAsString(loginRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        MockHttpSession session = new MockHttpSession();
        Account account = accountRepository.findByMail(mail).orElseThrow();
        session.setAttribute(LOGIN_ACCOUNT, account.getId());
        return session;
    }

    private <T> String MockPost(MockHttpSession session, String url, T t) throws Exception {
        return mockMvc.perform(post(url)
                        .content(objectMapper.writeValueAsString(t))
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(session))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private String MockPost(MockHttpSession session, String url) throws Exception {
        return mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(session))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private String MockDelete(MockHttpSession session, String url) throws Exception {
        return mockMvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(session))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private ResponseOKDto<GetAllPostResponseDto> MockGetPosts(MockHttpSession session, String url) throws Exception {
        String responseStr = mockMvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(session))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        return objectMapper.readValue(responseStr, new TypeReference<>() {});
    }

    private ResponseOKDto<GetAllInterestResponseDto> MockGetInterests(MockHttpSession session, String url) throws Exception {
        String responseStr = mockMvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(session))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        return objectMapper.readValue(responseStr, new TypeReference<>() {});
    }

    private ResponseOKDto<GetAllChatResponseDto> MockGetChats(MockHttpSession session, String url) throws Exception {
        String responseStr = mockMvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(session))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        return objectMapper.readValue(responseStr, new TypeReference<>() {});
    }

//    @Test
//    void deleteAccount() {
//    }
//
//    @Test
//    void getAccountInfo() {
//    }
//
//    @Test
//    void updateAccountInfo() {
//    }
//
//    @Test
//    void changePassword() {
//    }
//
//    @Test
//    void addActivityArea() {
//    }
}