package com.ddangnmarket.ddangmarkgetbackend.domain.chat;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatJpaRepository {

    private final EntityManager em;

    public Chat save(Chat chat){
        em.persist(chat);
        return chat;
    }

    public void delete(Chat chat){
        em.remove(chat);
    }

    public Optional<Chat> findById(Long id){
        return Optional.ofNullable(em.find(Chat.class, id));
    }

    public List<Chat> findAllByPostId(Long postId){
        return em.createQuery("select c from Chat c" +
                        " join fetch c.account a" +
                        " join fetch c.post p" +
                        " where p.id =: postId", Chat.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    public List<Chat> findAllByPostIds(Collection<Long> postIds){
        return em.createQuery("select c from Chat c" +
                " join fetch c.account a" +
                " join fetch c.post p" +
                " where p.id in :postIds ", Chat.class)
                .setParameter("postIds", postIds)
                .getResultList();
    }

    public List<Chat> findAllByAccount(Account account){
        return em.createQuery("select c from Chat c" +
                        " join fetch c.post p" +
                        " join fetch c.chatRoom cr" +
                        " where c.account = :account" +
                        " and p.seller <> :account", Chat.class)
                .setParameter("account", account)
                .getResultList();
    }

    public List<Chat> findAllByAccount(Long accountId){
        return em.createQuery("select c from Chat c" +
                " join fetch c.post p" +
                " join fetch c.chatRoom cr" +
                " join fetch p.seller s" +
                " where c.account.id = :accountId", Chat.class)
                .setParameter("accountId", accountId)
                .getResultList();
    }

    public Optional<Chat> findByAccountAndPost(Long accountId, Long postId){
        return em.createQuery("select c from Chat c" +
                        " join fetch c.post p" +
                        " where c.account.id = :accountId" +
                        " and p.id = :postId", Chat.class)
                .setParameter("accountId", accountId)
                .setParameter("postId", postId)
                .getResultStream()
                .findAny();
    }



    public List<Chat> findAll() {
        return em.createQuery("select c from Chat c", Chat.class).getResultList();
    }
}
