package com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage;

import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseOKDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.dto.GetMessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author SeunghyunYoo
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chats/messages")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @GetMapping
    public ResponseEntity<ResponseOKDto<GetMessageResponseDto>> getMessages(
            @RequestParam String roomId){
        List<ChatMessage> chatMessages = chatMessageService.findAllMessage(roomId);

        return new ResponseEntity<>(new ResponseOKDto<>(
                new GetMessageResponseDto(chatMessages)), HttpStatus.OK);
    }
}
