package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.domain.*;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.chat.ChatRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.DistrictRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.file.UploadFileRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.UpdatePostRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.event.PostSaleEvent;
import com.ddangnmarket.ddangmarkgetbackend.domain.purchase.PurchaseRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.sale.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private final ApplicationEventPublisher eventPublisher;

    public Long post(Post post){
        return postRepository.save(post).getId();
    }

    public Long post(Account account, String title, String desc, int price, CategoryTag categoryTag
            ,List<Long> fileIds) {
        Category category = categoryJpaRepository.findByCategoryTag(categoryTag);

        List<UploadFile> uploadFiles = uploadFileRepository.findAllByIds(fileIds);
        validateExistFileIds(fileIds, uploadFiles);

        Post post = createPostWithFiles(account, title, desc, price, category, uploadFiles);
        return postRepository.save(post).getId();

    }

    private void validateExistFileIds(List<Long> fileIds, List<UploadFile> uploadFiles) {
        if(uploadFiles.size() != fileIds.size()){
            throw new IllegalArgumentException("존재하지 않는 imageId 입니다.");
        }
    }

    private Post createPostWithFiles(Account account, String title, String desc, int price, Category category, List<UploadFile> uploadFiles) {
        Post post = Post.createPost(title, desc, price, category, account);
        if(uploadFiles.size() != 0){
            uploadFiles.forEach(post::addUploadFile);
        }
        return post;
    }

    public void updatePost(Account seller, Long postId, UpdatePostRequestDto updatePostRequestDto){
        validateIsSellerPost(postId, seller);
        Post post = findPostByIdOrThrow(postId);
        Category category = categoryJpaRepository.findByCategoryTag(
                updatePostRequestDto.getCategoryTag());

        post.updatePost(
                updatePostRequestDto.getTitle(),
                updatePostRequestDto.getDesc(),
                updatePostRequestDto.getPrice(),
                category
        );
    }

    private Post findPostByIdOrThrow(Long postId){
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

    private void validateIsSellerPost(Long postId, Account seller) {
        seller.getPosts().stream()
                .filter(post-> post.getId().equals(postId))
                .findAny().orElseThrow(()->
                        new IllegalArgumentException("해당 사용자의 게시글이 아닙니다."));
    }

    public void sale(Account seller, Long postId, Long chatId){
        validateIsSellerPost(postId, seller);

        Post post = findPostByIdOrThrow(postId);

        Chat chat = findChatByIdOrThrow(chatId);

        changeStatusToComplete(post, chat);

        Account buyer = chat.getAccount();

        saveSaleAndPurchaseHistory(seller, buyer, post);

        eventPublisher.publishEvent(new PostSaleEvent(post, buyer));
    }

    private Chat findChatByIdOrThrow(Long chatId) {
        return chatRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅입니다."));
    }

    private void changeStatusToComplete(Post post, Chat chat){
        resetOtherChatsStatus(post);
        chat.changeComplete();
        post.changeComplete();
    }

    private void resetOtherChatsStatus(Post post){
        if(post.getPostStatus().equals(PostStatus.COMPLETE)){
            deletePurchaseAndSale(post.getId());
            post.cancelSale();
        }
        if(post.getPostStatus().equals(PostStatus.RESERVE)){
            post.cancelReserve();
        }
    }

    private void deletePurchaseAndSale(Long postId) {
        Purchase purchase = purchaseRepository.findByPostId(postId).orElseThrow();
        purchaseRepository.delete(purchase);
        Sale sale = saleRepository.findByPostId(postId).orElseThrow();
        saleRepository.delete(sale);
    }

    private void saveSaleAndPurchaseHistory(Account seller, Account buyer, Post post){
        Sale sale = Sale.createSale(seller, post);
        Purchase purchase = Purchase.createPurchase(buyer, post);

        saleRepository.save(sale);
        purchaseRepository.save(purchase);
    }

    public void cancelSale(Account seller, Long postId){
        validateIsSellerPost(postId, seller);
        // TODO chatId를 받아올지, 전체 chat을 looping해서 상태를 바꿀지
        Post post = findPostByIdOrThrow(postId);
        if(post.getPostStatus().equals(PostStatus.COMPLETE)) {
            deletePurchaseAndSale(postId);
            post.cancelSale();
        }
    }

    public void changeReserve(Account seller, Long postId, Long chatId) {
        validateIsSellerPost(postId, seller);

        Post post = findPostByIdOrThrow(postId);
        // TODO 판매 완료된 건 예약 상태로 바꾸게 허용할지 말지
        //  validateSaleComplete(post);
        if(post.getPostStatus() == PostStatus.COMPLETE){
            deletePurchaseAndSale(postId);
            post.cancelSale();
        }
        Chat chat = findChatByIdOrThrow(chatId);
        post.changeReserve(chat);
    }

    public void cancelReserve(Account seller, Long postId){
        validateIsSellerPost(postId, seller);

        Post post = findPostByIdOrThrow(postId);
        post.cancelReserve();
    }

    public void pullPost(Account seller, Long postId){
        validateIsSellerPost(postId, seller);

        Post post = findPostByIdOrThrow(postId);
        post.pullPost();
    }

    public void delete(Account seller, Long postId){
        validateIsSellerPost(postId, seller);
        findPostByIdOrThrow(postId).deletePost();
    }

    private void validateSaleComplete(Post post) {
        if (post.getPostStatus().equals(PostStatus.COMPLETE)){
            throw new IllegalStateException("이미 판매 완료된 상품입니다.");
        }
    }

    public Post findPostAndAddViewCount(Long postId) {
        Post post = postRepository.findAllInfoById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
//        replyRepository.findByPostId(postId);
        post.addViewCount();
        return post;
    }

    public List<Post> findAll(Account account){
        List<District> districts = getDistrict(account);

        return postRepository.findAll(districts);
    }

    private List<District> getDistrict(Account account){
        List<District> districts = districtRepository.findAll();

        ActivityArea activityArea = account.getActivityArea();

        return districts.stream()
                .filter(activityArea::isAccessibleArea)
                .collect(Collectors.toList());
    }

    public List<Post> findAllByStatuses(Account account, List<PostStatus> postStatuses){
        List<District> districts = getDistrict(account);

        return postRepository.findAllByPostStatuses(districts, postStatuses);
    }

    public List<Post> findPostAllBySeller(Account account){
        return postRepository.findAllBySeller(account);
    }

    public List<Post> findPostAllBySellerAndStatuses(Account account, List<PostStatus> postStatuses){
        return postRepository.findAllBySellerAndPostStatuses(account, postStatuses);

    }

    public List<Post> findPostAllByCategory(Account account, CategoryTag categoryTag){
        List<District> districts = getDistrict(account);

        return postRepository.findAllByCategory(districts, categoryTag);
    }

    public List<Post> findAllByCategoryAndStatuses(Account account, CategoryTag categoryTag, List<PostStatus> postStatuses) {
        List<District> districts = getDistrict(account);

        return postRepository.findAllByCategoryAndPostStatuses(districts, categoryTag, postStatuses);
    }
}
