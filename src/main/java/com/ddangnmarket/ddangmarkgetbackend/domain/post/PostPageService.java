package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.domain.*;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostPageService {

    private final DistrictRepository districtRepository;
    private final PostRepository postRepository;

    public Page<Post> findPagePosts(Account account, List<PostStatus> statuses, Pageable pageable){
        List<District> districts = getDistrict(account);
        return postRepository.getPagePostByStatus(districts, statuses, pageable);
    }

    private List<District> getDistrict(Account account){
        List<District> districts = districtRepository.findAll();

        ActivityArea activityArea = account.getActivityArea();

        return districts.stream()
                .filter(activityArea::isAccessibleArea)
                .collect(Collectors.toList());
    }

    public Page<Post> findPagePostsBySeller(Account seller, List<PostStatus> postStatuses, Pageable pageable){
        return postRepository.getPagePostsBySellerAndStatus(seller, postStatuses, pageable);
    }

    public Page<Post> findPagePostsByCategory(
            Account account, CategoryTag categoryTag, List<PostStatus> postStatuses, Pageable pageable){
        List<District> districts = getDistrict(account);
        return postRepository.getPagePostsByStatusAndCategory(districts, postStatuses, categoryTag, pageable);
    }
}
