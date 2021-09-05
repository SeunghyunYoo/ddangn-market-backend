package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.domain.*;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.chat.ChatJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.DistrictJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.UpdatePostRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.purchase.PurchaseJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.sale.SaleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostJpaRepository postJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    private final ChatJpaRepository chatJpaRepository;
    private final DistrictJpaRepository districtJpaRepository;
    private final SaleJpaRepository saleJpaRepository;
    private final PurchaseJpaRepository purchaseJpaRepository;

    public Long post(Post post){
        return postJpaRepository.save(post).getId();
    }

    public Long post(String title, String desc, int price, CategoryTag categoryTag, Account account) {
        Category category = categoryJpaRepository.findByCategoryTag(categoryTag);

//        Post post = Post.createPost(title, desc, price, new PostCategory(category), account);
        Post post = Post.createPost(title, desc, price, category, account);
        return postJpaRepository.save(post).getId();
    }

    public List<Post> findAll(Account account){
        List<District> districts = getDistrict(account);

        return postJpaRepository.findAll(districts);
    }

    public List<Post> findAllByStatus(Account account, SaleStatus saleStatus){
        List<District> districts = getDistrict(account);

        return postJpaRepository.findAllByStatus(districts, saleStatus);
    }

    public List<Post> findAllByStatuses(Account account, List<SaleStatus> saleStatuses){
        List<District> districts = getDistrict(account);

        return postJpaRepository.findAllByStatuses(districts, saleStatuses);
    }

    public List<Post> findPostAllBySeller(Account account){
        return postJpaRepository.findAllBySeller(account);
    }

    public List<Post> findPostAllBySellerAndStatus(Account account, SaleStatus saleStatus){
        return postJpaRepository.findAllBySellerAndStatus(account, saleStatus);
    }

    public List<Post> findPostAllBySellerAndStatuses(Account account, List<SaleStatus> saleStatuses){
        return postJpaRepository.findAllBySellerAndStatuses(account, saleStatuses);
    }

    public List<Post> findPostAllByCategory(Account account, CategoryTag categoryTag){
        List<District> districts = getDistrict(account);

        return postJpaRepository.findAllByCategory(districts, categoryTag);
    }

    public List<Post> findAllByCategoryAndStatus(Account account, CategoryTag categoryTag, SaleStatus saleStatus){
        List<District> districts = getDistrict(account);

        return postJpaRepository.findAllByCategoryAndStatus(districts, categoryTag, saleStatus);
    }

    public List<Post> findAllByCategoryAndStatuses(Account account, CategoryTag categoryTag, List<SaleStatus> saleStatuses) {
        List<District> districts = getDistrict(account);

        return postJpaRepository.findAllByCategoryAndStatuses(districts, categoryTag, saleStatuses);
    }

    public List<Post> findAllPurchase(Account account){
        return postJpaRepository.findAllPurchase(account);
    }

    /**
     * 사용자가 삭제 요청시 -> 구매완료된 상품일 경우 hide로 변경
     * @param postId
     */
    public void deleteById(Long postId){
        // TODO 삭제 요청시 -> hide로 변경
        Post post = findById(postId);
        postJpaRepository.delete(post);
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

        if(post.getSaleStatus().equals(SaleStatus.COMPLETE)){
            post.cancelSale();
        }
        if(post.getSaleStatus().equals(SaleStatus.RESERVE)){
            post.cancelReserve();
        }

        Chat chat = chatJpaRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅입니다."));
        chat.changeComplete();

        Account buyer = chat.getAccount();

        Sale sale = Sale.createSale(seller, post);
        Purchase purchase = Purchase.createPurchase(buyer, post);

        saleJpaRepository.save(sale);
        purchaseJpaRepository.save(purchase);
    }

    public void cancelSale(Long postId){
        // TODO chatId를 받아올지, 전체 chat을 looping해서 상태를 바꿀지
        Post post = findById(postId);
        if(post.getSaleStatus().equals(SaleStatus.COMPLETE)) {
            post.cancelSale();
        }
    }

    public void changeReserve(Long postId, Long chatId) {
        Post post = findById(postId);
        // TODO 판매 완료된 건 예약 상태로 바꾸게 허용할지 말지
        //  validateSaleComplete(post);
        if(post.getSaleStatus() == SaleStatus.COMPLETE){
            post.cancelSale();
        }

        // 이런 validation이 필요한가?
        Chat chat = chatJpaRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅입니다."));

        post.changeReserve(chat);
    }

    public void cancelReserve(Long postId){
        Post post = findById(postId);
        post.cancelReserve();
    }

    private void validateSaleComplete(Post post) {
        if (post.getSaleStatus().equals(SaleStatus.COMPLETE)){
            throw new IllegalStateException("이미 판매 완료된 상품입니다.");
        }
    }

    public Post findById(Long postId){
        return postJpaRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

    public Post findByIdWithSaleAndPurchase(Long postId){
        return postJpaRepository.findByIdWithSaleAndPurchase(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

    private List<District> getDistrict(Account account){
        List<District> districts = districtJpaRepository.findAll();

        District accountDistrict = account.getActivityArea().getDistrict();
        Integer range = account.getActivityArea().getRange();

        return districts.stream()
                .filter(district ->
                        accountDistrict.getPosition().calcDiff(district.getPosition()) <= range)
                .collect(Collectors.toList());
    }

}
