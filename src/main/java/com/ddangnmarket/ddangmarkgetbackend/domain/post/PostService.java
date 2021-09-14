package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.domain.*;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.chat.ChatRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.DistrictRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.file.UploadFileRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.UpdatePostRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.purchase.PurchaseRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.reply.ReplyRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.sale.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    private final ChatRepository chatRepository;
    private final DistrictRepository districtRepository;
    private final SaleRepository saleRepository;
    private final PurchaseRepository purchaseRepository;
    private final UploadFileRepository uploadFileRepository;

    public Long post(Post post){
        return postRepository.save(post).getId();
    }

    public Long post(String title, String desc, int price, CategoryTag categoryTag, Account account) {
        Category category = categoryJpaRepository.findByCategoryTag(categoryTag);

//        Post post = Post.createPost(title, desc, price, new PostCategory(category), account);
        Post post = Post.createPost(title, desc, price, category, account);
        return postRepository.save(post).getId();

    }

    public Long post(String title, String desc, int price, CategoryTag categoryTag
            ,List<Long> fileIds, Account account) {
        Category category = categoryJpaRepository.findByCategoryTag(categoryTag);

        List<UploadFile> uploadFiles = uploadFileRepository.findAllByIds(fileIds);
        Post post = Post.createPost(title, desc, price, category, account);
        if(uploadFiles.size() != 0){
            uploadFiles.forEach(post::addUploadFile);
        }
        return postRepository.save(post).getId();

    }

    public void delete(Long postId){
        findById(postId).deletePost();
    }

    public List<Post> findAll(Account account){
        List<District> districts = getDistrict(account);

        return postRepository.findAll(districts);
    }

    public List<Post> findAllByStatus(Account account, PostStatus postStatus){
        List<District> districts = getDistrict(account);


        return postRepository.findAllByPostStatus(districts, postStatus);

    }

    public List<Post> findAllByStatuses(Account account, List<PostStatus> postStatuses){
        List<District> districts = getDistrict(account);


        return postRepository.findAllByPostStatuses(districts, postStatuses);

    }

    public List<Post> findPostAllBySeller(Account account){
        return postRepository.findAllBySeller(account);
    }


    public List<Post> findPostAllBySellerAndStatus(Account account, PostStatus postStatus){
        return postRepository.findAllBySellerAndPostStatus(account, postStatus);
    }

    public List<Post> findPostAllBySellerAndStatuses(Account account, List<PostStatus> postStatuses){
        return postRepository.findAllBySellerAndPostStatuses(account, postStatuses);

    }

    public List<Post> findPostAllByCategory(Account account, CategoryTag categoryTag){
        List<District> districts = getDistrict(account);

        return postRepository.findAllByCategory(districts, categoryTag);
    }

    public List<Post> findAllByCategoryAndPostStatus(Account account, CategoryTag categoryTag, PostStatus postStatus){
        List<District> districts = getDistrict(account);


        return postRepository.findAllByCategoryAndPostStatus(districts, categoryTag, postStatus);

    }

    public List<Post> findAllByCategoryAndStatuses(Account account, CategoryTag categoryTag, List<PostStatus> postStatuses) {
        List<District> districts = getDistrict(account);


        return postRepository.findAllByCategoryAndPostStatuses(districts, categoryTag, postStatuses);

    }

    public List<Post> findAllPurchase(Account account){
        return postRepository.findAllPurchase(account);
    }

    /**
     * 사용자가 삭제 요청시 -> 구매완료된 상품일 경우 hide로 변경
     * @param postId
     */
    public void deleteById(Long postId){
        // TODO 삭제 요청시 -> hide로 변경
        Post post = findById(postId);
    }

    public void updatePost(Long postId, UpdatePostRequestDto updatePostRequestDto){
        Post post = findById(postId);
        Category category = categoryJpaRepository.findByCategoryTag(
                updatePostRequestDto.getCategoryTag());

        post.updatePost(
                updatePostRequestDto.getTitle(),
                updatePostRequestDto.getDesc(),
                updatePostRequestDto.getPrice(),
                category
        );
    }

    public void sale(Account seller, Long postId, Long chatId){
        Post post = findById(postId);

        if(post.getPostStatus().equals(PostStatus.COMPLETE)){
            post.cancelSale();
        }
        if(post.getPostStatus().equals(PostStatus.RESERVE)){
            post.cancelReserve();
        }

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅입니다."));
//        chat.changeComplete();
        post.changeComplete();

        Account buyer = chat.getAccount();

        Sale sale = Sale.createSale(seller, post);
        Purchase purchase = Purchase.createPurchase(buyer, post);

        saleRepository.save(sale);
        purchaseRepository.save(purchase);
    }

    public void cancelSale(Long postId){
        // TODO chatId를 받아올지, 전체 chat을 looping해서 상태를 바꿀지
        Post post = findById(postId);
        if(post.getPostStatus().equals(PostStatus.COMPLETE)) {
            post.cancelSale();
        }
    }

    public void changeReserve(Long postId, Long chatId) {
        Post post = findById(postId);
        // TODO 판매 완료된 건 예약 상태로 바꾸게 허용할지 말지
        //  validateSaleComplete(post);
        if(post.getPostStatus() == PostStatus.COMPLETE){
            post.cancelSale();
        }

        // 이런 validation이 필요한가?
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅입니다."));

        post.changeReserve(chat);
    }

    public void cancelReserve(Long postId){
        Post post = findById(postId);
        post.cancelReserve();
    }

    private void validateSaleComplete(Post post) {
        if (post.getPostStatus().equals(PostStatus.COMPLETE)){
            throw new IllegalStateException("이미 판매 완료된 상품입니다.");
        }
    }

    private final ReplyRepository replyRepository;
    public Post findPost(Long postId) {
        Post post = postRepository.findAllInfoById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
//        replyRepository.findByPostId(postId);
        post.addViewCount();
        return post;
    }

    private Post findById(Long postId){

        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

    public Post findByIdWithSaleAndPurchase(Long postId){
        return postRepository.findWithSaleAndPurchaseById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

    private List<District> getDistrict(Account account){
        List<District> districts = districtRepository.findAll();

        District accountDistrict = account.getActivityArea().getDistrict();
        Integer range = account.getActivityArea().getRange();

        return districts.stream()
                .filter(district ->
                        accountDistrict.getPosition().calcDiff(district.getPosition()) <= range)
                .collect(Collectors.toList());
    }
}
