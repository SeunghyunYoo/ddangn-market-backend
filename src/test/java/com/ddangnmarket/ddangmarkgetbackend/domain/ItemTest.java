package com.ddangnmarket.ddangmarkgetbackend.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.Set;

import static com.ddangnmarket.ddangmarkgetbackend.domain.LocationTag.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ItemTest {

    @Autowired
    EntityManager em;

    @PostConstruct
    void init(){
//        Arrays.stream(LocationTag.class.getEnumConstants()).map(locationTag -> {
//            new Location().setLocationTag(locationTag);
//        });
    }

    @Test
    void item(){

        Account seller = new Account();
        seller.setName("seller");
        seller.setAddress(new Address(GUMI));
        Account buyer1 = new Account();
        buyer1.setName("buyer1");
        buyer1.setAddress(new Address(GUMI));

        Location location = new Location();
        location.setLocationTag(GUMI);
        location.setAccount(buyer1);

        em.persist(seller);
        em.persist(buyer1);
        em.persist(location);

        Account buyer2 = new Account();
        buyer2.setName("buyer2");
        buyer2.setAddress(new Address(JEONGJA));

        em.persist(buyer2);


//        em.flush();
//        em.clear();

        Item item = new Item();
        item.setName("book");
        item.setSeller(seller);

        em.persist(item);


        em.flush();
        em.clear();

        Item findItem = em.find(Item.class, item.getId());
        assertThat(findItem.getName()).isEqualTo("book");
        assertThat(findItem.getSeller().getName()).isEqualTo("seller");

        /*
        System.out.println(seller.getAddress().getDong());

        Account account = em.find(Account.class, buyer1.getId());
        em.remove(account);
        */

    }

    @Test
    void locationTest(){

        Account seller = new Account();
        seller.setName("seller");
        seller.setAddress(new Address(GUMI));
        Account buyer1 = new Account();
        buyer1.setName("buyer1");
        buyer1.setAddress(new Address(GUMI));

//        Location location = new Location();
//        location.setLocationTag(GUMI);
//        location.setAccount(buyer1);

        em.persist(seller);
        em.persist(buyer1);

        em.flush();
        em.clear();

        assertThat(buyer1.getLocations().size()).isEqualTo(1);
        System.out.println("locations" + buyer1.getLocations());

        Account findBuyer = em.find(Account.class, buyer1.getId());
        assertThat(findBuyer.getLocations().size()).isEqualTo(1);

        System.out.println("buyer1 = " + buyer1);

    }


    @Test
    void AddressToDefaultLocationSave(){
        Account account = new Account();
        account.setName("account");
        account.setAddress(new Address(GUMI));

        em.persist(account);

        em.flush();
        em.clear();

        Account findAcc = em.find(Account.class, account.getId());
//        assertThat(findAcc.getLocations().size()).isEqualTo(1);


        Set<Location> locations = findAcc.getLocations();
        Location location = new Location();
        location.setLocationTag(findAcc.getAddress().getDong());
        location.setAccount(findAcc);
        locations.add(location);

        assertThat(findAcc.getLocations().size()).isEqualTo(1);

    }
}