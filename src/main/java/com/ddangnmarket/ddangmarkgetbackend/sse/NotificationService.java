package com.ddangnmarket.ddangmarkgetbackend.sse;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.event.PostSaleEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;


/**
 * @author SeunghyunYoo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SseEmitterRepository sseEmitterRepository;

    @Async
    @EventListener
    public void handlePostSaleEvent(PostSaleEvent postSaleEvent){
        Post post = postSaleEvent.getPost();

        Account buyer = postSaleEvent.getBuyer();
        String nickname = buyer.getNickname();

        SseEmitter emitter = sseEmitterRepository.get(nickname);

        try {
            emitter.send(SseEmitter.event().name("message").data("notification", MediaType.APPLICATION_JSON));
            emitter.complete();
        } catch (Exception ex) {
            emitter.completeWithError(ex);
        }

        emitter.onCompletion(() -> {
            sseEmitterRepository.remove(nickname);
        });
    }

    public SseEmitter saveSseEmitter(String id, SseEmitter sseEmitter){
        return sseEmitterRepository.put(id, sseEmitter);
    }

    public SseEmitter saveSseEmitter(String id){
        SseEmitter emitter = new SseEmitter(10*60*1000L);
//        SseEmitter emitter = new SseEmitter(60*1000L);
        // TODO 다른 쓰레드에서 진행되는건가?
        handleTimeoutEmitter(id, emitter);
        return sseEmitterRepository.put(id, emitter);
    }

    private void handleTimeoutEmitter(String id, SseEmitter emitter) {
        emitter.onTimeout(() -> {
            emitter.complete();
            log.info("notification timeout at {}", LocalDateTime.now());
        });

        emitter.onError((ex)-> {
            emitter.completeWithError(ex);
            log.info("notification error at {}",LocalDateTime.now(), ex);
        });

        emitter.onCompletion(() -> {
            sseEmitterRepository.remove(id);
        });
    }

    public void removeSseEmitter(String id){
        sseEmitterRepository.remove(id);
    }

    public SseEmitter getSseEmitter(String id){
        return sseEmitterRepository.get(id);
    }
}
