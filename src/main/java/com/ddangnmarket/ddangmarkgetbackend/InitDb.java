package com.ddangnmarket.ddangmarkgetbackend;

import com.ddangnmarket.ddangmarkgetbackend.domain.*;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.DistrictRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.Position;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong.*;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.initCategory();
        initService.initDistrict();
        initService.initAccountPost();
//        initService.initAccountPostV2();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService{
        private final EntityManager em;
        private final CategoryJpaRepository categoryJpaRepository;
        private final DistrictRepository districtRepository;

        public void initCategory(){
            for(CategoryTag categoryTag : CategoryTag.values()){
                 em.persist(new Category(categoryTag));
            }
        }

        public void initDistrict(){
            em.persist(new District(SAMPYEONG, new Position(-1, 3)));
            em.persist(new District(PANGYO, new Position(0, 2)));
            em.persist(new District(BACKHYUN, new Position(-1, 0)));
            em.persist(new District(UNJUNG, new Position(-3, 1)));
            em.persist(new District(GUMI1, new Position(-1, -1)));
            em.persist(new District(GEUMGOK, new Position(-1, 0)));
            em.persist(new District(JEONGJA1, new Position(0, 0)));
            em.persist(new District(JEONGJA2, new Position(0, 1)));
            em.persist(new District(JEONGJA3, new Position(-1, 1)));
            em.persist(new District(GUMI, new Position(0, -2)));
            em.persist(new District(SUNAE1, new Position(0, 1)));
            em.persist(new District(SUNAE2, new Position(1, 0)));
            em.persist(new District(SUNAE3, new Position(2, 0)));
            em.persist(new District(SEOHYEON1, new Position(3, 1)));
            em.persist(new District(SEOHYEON2, new Position(2, 0)));
            em.persist(new District(BUNDANG, new Position(2, 0)));
            em.persist(new District(IMAE1, new Position(0, 2)));
            em.persist(new District(IMAE2, new Position(1, 2)));
            em.persist(new District(YATAP1, new Position(0, 3)));
            em.persist(new District(YATAP2, new Position(0, 4)));
            em.persist(new District(YATAP3, new Position(2, 3)));
        }

        public void initAccountPost(){
            Account account1 = new Account("account1", "000-0000-0000", "account1@gmail.com", "00000000");
            Account account2= new Account("account2", "000-0000-0000", "account2@gmail.com", "00000000");
            Account account3 = new Account("account3", "000-0000-0000", "account3@gmail.com", "00000000");

            ActivityArea activeArea1 = ActivityArea.createActiveArea(districtRepository.findByDong(Dong.GUMI), 0);
            ActivityArea activeArea2 = ActivityArea.createActiveArea(districtRepository.findByDong(JEONGJA1), 0);
            ActivityArea activeArea3 = ActivityArea.createActiveArea(districtRepository.findByDong(Dong.UNJUNG), 0);

            account1.setActivityArea(activeArea1);
            account2.setActivityArea(activeArea2);
            account3.setActivityArea(activeArea3);

            em.persist(account1);
            em.persist(account2);
            em.persist(account3);

            Category digital = categoryJpaRepository.findByCategoryTag(CategoryTag.DIGITAL);
            Category book = categoryJpaRepository.findByCategoryTag(CategoryTag.BOOK);
            Category clothes = categoryJpaRepository.findByCategoryTag(CategoryTag.CLOTHES);
            Category furniture = categoryJpaRepository.findByCategoryTag(CategoryTag.FURNITURE);

            Post post1 = Post.createPost("맥북판매", "맥북팔아요", 100000, digital, account1);
            em.persist(post1);
            Post post2 = Post.createPost("스프링 책 판매", "스프링 책 팔아요", 10000, book, account1);
            em.persist(post2);
            Post post3 = Post.createPost("티셔츠 판매 ", "티셔츠 팔아요", 100000, clothes, account1);
            em.persist(post3);
            Post post4 = Post.createPost("책상 판매", "책상 팔아요", 10000, furniture, account1);
            em.persist(post4);

            Chat chat1 = Chat.createChat(post1, account2);
            em.persist(chat1);
            Chat chat2 = Chat.createChat(post1, account3);
            em.persist(chat2);

            Chat chat3 = Chat.createChat(post2, account2);
            em.persist(chat3);
        }

        public void initAccountPostV2(){
            Account account1 = new Account("account1", "000-0000-0000", "account1@gmail.com", "00000000");
            Account account2= new Account("account2", "000-0000-0000", "account2@gmail.com", "00000000");
            Account account3 = new Account("account3", "000-0000-0000", "account3@gmail.com", "00000000");

            ActivityArea activeArea1 = ActivityArea.createActiveArea(districtRepository.findByDong(Dong.GUMI), 0);
            ActivityArea activeArea2 = ActivityArea.createActiveArea(districtRepository.findByDong(JEONGJA1), 0);
            ActivityArea activeArea3 = ActivityArea.createActiveArea(districtRepository.findByDong(Dong.UNJUNG), 0);

            account1.setActivityArea(activeArea1);
            account2.setActivityArea(activeArea2);
            account3.setActivityArea(activeArea3);

            em.persist(account1);
            em.persist(account2);
            em.persist(account3);

            Category digital = categoryJpaRepository.findByCategoryTag(CategoryTag.DIGITAL);
            Category book = categoryJpaRepository.findByCategoryTag(CategoryTag.BOOK);
            Category clothes = categoryJpaRepository.findByCategoryTag(CategoryTag.CLOTHES);
            Category furniture = categoryJpaRepository.findByCategoryTag(CategoryTag.FURNITURE);

            Post post1 = Post.createPost("맥북판매", "맥북팔아요", 100000, digital, account1);
            em.persist(post1);
            Post post2 = Post.createPost("스프링 책 판매", "스프링 책 팔아요", 10000, book, account1);
            em.persist(post2);
            Post post3 = Post.createPost("티셔츠 판매 ", "티셔츠 팔아요", 100000, clothes, account1);
            em.persist(post3);
            Post post4 = Post.createPost("책상 판매", "책상 팔아요", 10000, furniture, account1);
            em.persist(post4);

            int i = 0;
            String title = "title";
            String desc = "description";
            for (Account account : List.of(account1, account2, account3)) {
                for (CategoryTag categoryTag :CategoryTag.values()){
                    Category category = categoryJpaRepository.findByCategoryTag(categoryTag);

                    em.persist(
                            Post.createPost(title + " " + i,
                                    desc + " " + i +" "+ account.getNickname(),
                                    10000, category, account));
                    i+=1;
                }
            }

            Chat chat1 = Chat.createChat(post1, account2);
            em.persist(chat1);
            Chat chat2 = Chat.createChat(post1, account3);
            em.persist(chat2);

            Chat chat3 = Chat.createChat(post2, account2);
            em.persist(chat3);
        }


    }
}
