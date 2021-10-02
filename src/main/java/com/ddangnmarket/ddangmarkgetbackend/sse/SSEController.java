package com.ddangnmarket.ddangmarkgetbackend.sse;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.event.PostSaleEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;

/**
 * @author SeunghyunYoo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SSEController {

    private final NotificationService notificationService;

//    @GetMapping("/notification")
    public SseEmitter notification(String id){
        SseEmitter emitter = new SseEmitter(10*60*1000L);

//        sseRepository.put(id, emitter);
        notificationService.saveSseEmitter(id, emitter);

        emitter.onTimeout(() -> {
//            sseRepository.remove(id);
            notificationService.removeSseEmitter(id);
            emitter.complete();
            log.info("notification timeout at {}",LocalDateTime.now());
        });

        emitter.onCompletion(() -> {
//            postEmitter.remove(id);
//            sseRepository.remove(id);
            notificationService.removeSseEmitter(id);
        });

        emitter.onError((ex)-> {
            emitter.completeWithError(ex);
//            sseRepository.remove(id);
            notificationService.removeSseEmitter(id);
            log.info("notification error at {}",LocalDateTime.now(), ex);
        });
//        sseRepository.put(id, emitter);
        notificationService.saveSseEmitter(id, emitter);

        return emitter;
    }

    @GetMapping("/notification")
    public SseEmitter notificationV2(String id){
        SseEmitter emitter = notificationService.saveSseEmitter(id);
        return emitter;
    }
}
