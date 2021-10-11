package com.ddangnmarket.ddangmarkgetbackend.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/notification")
    public SseEmitter notification(String id){
        SseEmitter emitter = notificationService.saveSseEmitter(id);
        return emitter;
    }
}
