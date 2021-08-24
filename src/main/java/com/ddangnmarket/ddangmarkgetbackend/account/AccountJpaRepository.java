package com.ddangnmarket.ddangmarkgetbackend.account;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountJpaRepository {

    private final EntityManager em;

    public Account save(Account account){
        em.persist(account);
        return account;
    }

    public void delete(Account account){
        em.remove(account);
    }

    public List<Account> findAll() {
        return em.createQuery("select a from Account a", Account.class)
                .getResultList();
    }

    public Optional<Account> findById(Long id) {
        Account account = em.find(Account.class, id);
        return Optional.ofNullable(account);
    }

    public Optional<Account> findByMail(String mail){
        return em.createQuery("select a from Account a" +
                " where a.mail = :mail", Account.class)
                .setParameter("mail", mail)
                .getResultStream()
                .findAny();
    }

    public boolean existsByMail(String mail){
        return !em.createQuery("select a from Account a" +
                        " where a.mail = :mail", Account.class)
                .setParameter("mail", mail).getResultList().isEmpty();
    }

}
