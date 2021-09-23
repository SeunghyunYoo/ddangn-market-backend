package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.domain.*;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.chat.ChatRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.DistrictRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.file.UploadFileRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.UpdatePostRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.purchase.PurchaseRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.sale.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostPageService {

    private final PostRepository postRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    private final ChatRepository chatRepository;
    private final DistrictRepository districtRepository;
    private final SaleRepository saleRepository;
    private final PurchaseRepository purchaseRepository;
    private final UploadFileRepository uploadFileRepository;


    public Page<Post> findPagePosts(Account account, int page, int size){
        List<District> districts = getDistrict(account);
        PageRequest pageRequest = PageRequest.of(page, size);
        return postRepository.findPagePosts(pageRequest, districts);
    }

    public Page<Post> findPagePostsByStatuses(Account account, List<PostStatus> postStatuses, int page, int size){
        List<District> districts = getDistrict(account);
        PageRequest pageRequest = PageRequest.of(page, size);
        return postRepository.findPagePostsByStatuses(pageRequest, postStatuses, districts);
    }

    private List<District> getDistrict(Account account){
        List<District> districts = districtRepository.findAll();

        ActivityArea activityArea = account.getActivityArea();

        return districts.stream()
                .filter(activityArea::isAccessibleArea)
                .collect(Collectors.toList());
    }


    public Page<Post> findPagePostsBySeller(Account seller, int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return postRepository.findPagePostsBySeller(pageRequest, seller);
    }

    public Page<Post> findPagePostsBySellerAndStatuses(
            Account seller, List<PostStatus> postStatuses, int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return postRepository.findPagePostsBySellerAndStatuses(pageRequest, seller, postStatuses);
    }

    public Page<Post> findPagePostsByCategory(
            Account account, CategoryTag categoryTag, int page, int size){
        List<District> districts = getDistrict(account);
        PageRequest pageRequest = PageRequest.of(page, size);
        return postRepository.findPagePostsByCategory(pageRequest, categoryTag, districts);
    }

    public Page<Post> findPagePostsByCategoryAndStatuses(
            Account account, CategoryTag categoryTag, List<PostStatus> postStatuses, int page, int size) {
        List<District> districts = getDistrict(account);
        PageRequest pageRequest = PageRequest.of(page, size);
        return postRepository.findPagePostsByCategoryAndStatuses(pageRequest, categoryTag, postStatuses, districts);
    }
}
