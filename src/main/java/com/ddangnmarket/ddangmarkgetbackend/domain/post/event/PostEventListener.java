package com.ddangnmarket.ddangmarkgetbackend.domain.post.event;

import com.ddangnmarket.ddangmarkgetbackend.config.redis.TopicConst;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.dto.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SeunghyunYoo
 */
@Slf4j
@Async
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostEventListener {

    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessageSendingOperations messageTemplate;
    private final Map<String, ChannelTopic> topics;
    private final Map<String, SseEmitter> postEmitter;


//    @EventListener
    public void handlePostSaleEvent(PostSaleEvent postSaleEvent){
        Post post = postSaleEvent.getPost();
        log.info(post.getId() + "saled");

        Account buyer = postSaleEvent.getBuyer();
        String nickname = buyer.getNickname();

        // TODO 메세지 전달
//        redisTemplate.convertAndSend(
////                topics.get(TopicConst.NOTIFICATION_POST).getTopic(),
////                new PostSaleMessageDto(MessageType.NOTIFICATION_POST, "판매완료", post.getId()));
        messageTemplate.convertAndSend(
                "/sub/api/user/" + nickname +"/notification/post",
                new PostSaleMessageDto(MessageType.NOTIFICATION_POST, "판매완료", post.getId()));


        SseEmitter emitter = postEmitter.get(nickname);

        try {
            log.info("postEmitter send Message ======");
            emitter.send("/test" + " @ " + new Date());
            // we could send more events
            emitter.complete();
        } catch (Exception ex) {
            emitter.completeWithError(ex);
        }

    }
}
