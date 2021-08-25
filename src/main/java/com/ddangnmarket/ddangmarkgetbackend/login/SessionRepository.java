package com.ddangnmarket.ddangmarkgetbackend.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class SessionRepository {

    private final EntityManager em;

    public void save(SessionInfo sessionInfo){
        em.persist(sessionInfo);
    }
}
