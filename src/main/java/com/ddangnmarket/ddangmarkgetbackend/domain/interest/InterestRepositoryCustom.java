package com.ddangnmarket.ddangmarkgetbackend.domain.interest;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Interest;

import java.util.List;
import java.util.Optional;

public interface InterestRepositoryCustom {

    List<Interest> findAllByAccount(Account account);

    Optional<Interest> findByPostId(Long postId);
}
