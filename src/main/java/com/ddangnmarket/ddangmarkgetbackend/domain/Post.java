package com.ddangnmarket.ddangmarkgetbackend.domain;

import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"title", "desc", "price", "viewCount", "chatCount", "interestCount", "uploadFiles"}, callSuper = false)
// 같은 내용의 게시글 등록 허용
public class Post extends DeleteEntity{

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    @Lob
    private String desc;

    @OneToMany(mappedBy = "post")
    private List<UploadFile> uploadFiles = new ArrayList<>();

    private int price;

    private int viewCount;

    private int chatCount;

    private int interestCount;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="category_id")
    private Category category;

//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Chat> chats = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;

    @OneToOne(mappedBy = "post", fetch = FetchType.LAZY, orphanRemoval = true)
    private Sale sale;

    @OneToOne(mappedBy = "post", fetch = FetchType.LAZY, orphanRemoval = true)
    private Purchase purchase;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies;

    // == 생성 메서드 == //
    public static Post createPost(String title, String desc, int price, Category category, Account seller){
        Post post = new Post();
        post.title = title;
        post.desc = desc;
        post.price = price;
        post.category = category;
        post.seller = seller;
        post.postStatus = PostStatus.NEW;
        post.district = seller.getActivityArea().getDistrict();
        post.viewCount = 0;
        post.chatCount = 0;
        post.interestCount = 0;
        seller.addPost(post);
        return post;
    }

    //== 연관관계 메서드 ==/


//    public void setSeller(Account seller) {
//        this.seller = seller;
//        seller.addPost(this);
//    }

    //== 바즈니스 로직 ==//

    public void addUploadFile(UploadFile file){
        uploadFiles.add(file);
        file.setPost(this);
    }

    public void deletePost(){
        super.delete();
        if(sale != null){
            sale.delete();
        }
        if(purchase != null){
            purchase.delete();
        }
        if(chats != null){
            chats.forEach(Chat::delete);
        }
//        chats.forEach(Chat::disconnectPost);
    }

    public void absoluteDeletePost(){
        if(sale != null){
            sale = null;
        }
        if(purchase != null){
            purchase = null;
        }
//        if(chats != null){
//            chats.forEach(chat -> {
//                chat.setPost(null);
//            });
//        }
        this.chats = null;
        this.seller = null;
    }

    public void addViewCount(){
        this.viewCount +=1;
    }

    public void addInterestCount(){
        this.interestCount +=1;
    }

    public void addChat(Chat chat){
        chats.add(chat);
        this.chatCount += 1;
    }

    public void updatePost(String title, String desc, int price, Category category){
        this.title = title;
        this.desc = desc;
        this.price = price;
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeComplete(){
        postStatus = PostStatus.COMPLETE;
    }

    /**
     * orphanRemoval 적용 -> null로 연관관계 끊으면 자동 삭제
     * repository delete operation 필요 x
     */
    public void cancelSale(){
        sale = null;
        purchase = null;
        postStatus = PostStatus.NEW;
        // mappedBy라 여기서 변경 못함 Chat에서
//        chats.forEach(Chat::changeNone);
    }

    /**
     * 다른 chat의 구매 희망자로 reserve 변경
     * @param chat
     */
    public void changeReserve(Chat chat){
        // 다른 chat으로 예약 변경 -> 기존의 reserved 였던 것들 취소
        // -> target chat 예약으로 지정
//        chats.stream()
//               .filter(c -> c.getPostStatus() == PostStatus.RESERVE)
//               .findAny()
//               .ifPresent(Chat::changeNone);
//        chats.forEach(Chat::changeNone);
//        chat.changeReserve();

        if (postStatus == PostStatus.COMPLETE) {
            sale = null;
            purchase = null;
        }

        postStatus = PostStatus.RESERVE;
    }

    public void cancelReserve(){
//        chats.forEach(Chat::changeNone);
        postStatus = PostStatus.NEW;
    }

    public void changeDistrict(District district){
        this.district = district;
    }

    public void addReply(Reply reply){
        replies.add(reply);
    }

    public void removeReply(Reply reply){
        replies.remove(reply);
    }
}
