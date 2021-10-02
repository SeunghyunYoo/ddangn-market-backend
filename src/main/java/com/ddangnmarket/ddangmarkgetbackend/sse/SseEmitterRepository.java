package com.ddangnmarket.ddangmarkgetbackend.sse;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SeunghyunYoo
 */
@Repository
public class SseEmitterRepository {

    private final Map<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();

    public SseEmitter put(String id, SseEmitter sseEmitter){
        sseEmitterMap.put(id, sseEmitter);
        return sseEmitter;
    }

    public void remove(String id){
        sseEmitterMap.remove(id);
    }

    public SseEmitter get(String nickname) {
        return sseEmitterMap.get(nickname);
    }
}
