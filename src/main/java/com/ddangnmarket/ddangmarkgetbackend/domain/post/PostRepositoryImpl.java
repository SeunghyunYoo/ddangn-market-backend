package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.domain.*;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.search.PostSearchCondition;
import com.ddangnmarket.ddangmarkgetbackend.utils.repository.Querydsl4RepositorySupport;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.ddangnmarket.ddangmarkgetbackend.domain.QAccount.*;
import static com.ddangnmarket.ddangmarkgetbackend.domain.QCategory.*;
import static com.ddangnmarket.ddangmarkgetbackend.domain.QDistrict.*;
import static com.ddangnmarket.ddangmarkgetbackend.domain.QPost.*;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author SeunghyunYoo
 */
@Repository
public class PostRepositoryImpl extends Querydsl4RepositorySupport implements PostCustomRepository{

    private final JPAQueryFactory queryFactory;
    public PostRepositoryImpl(EntityManager em) {
        super(Post.class);
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<Post> getPagePosts(List<District> districts, Pageable pageable){
        return applyPagination(pageableWithOrderAt(pageable), query -> query
                .selectFrom(post)
                .join(post.category, category).fetchJoin()
                .join(post.seller, account).fetchJoin()
                .join(post.district, district).fetchJoin()
                .where(defaultCond(districts)));
    }

    public Page<Post> getPagePostByStatus(
            List<District> districts, List<PostStatus> postStatuses, Pageable pageable){
        return applyPagination(pageableWithOrderAt(pageable),
                query -> query
                        .selectFrom(post)
                        .join(post.category, category).fetchJoin()
                        .join(post.seller, account).fetchJoin()
                        .join(post.district, district).fetchJoin()
                        .where(postStatusCond(districts, postStatuses)));
    }

    public Page<Post> getPagePostBySearch(
            List<District> districts, PostSearchCondition condition, Pageable pageable){
        return applyPagination(pageableWithOrderAt(pageable),
                query -> query
                        .selectFrom(post)
                        .join(post.category, category).fetchJoin()
                        .join(post.seller, account).fetchJoin()
                        .join(post.district, district).fetchJoin()
                        .where(
                                defaultCond(districts),
                                searchCond(condition)
                        ));
    }

    public Page<Post> getPagePostsByStatusAndCategory(
            List<District> districts, List<PostStatus> postStatuses, CategoryTag categoryTag, Pageable pageable){
        return applyPagination(pageableWithOrderAt(pageable),
                query -> query
                        .selectFrom(post)
                        .join(post.seller, account).fetchJoin()
                        .join(post.district, district).fetchJoin()
                        .where(postStatusAndCategoryCond(districts, postStatuses, categoryTag)));
    }

    public Page<Post> getPagePostsBySellerAndStatus(
            Account seller, List<PostStatus> postStatuses, Pageable pageable){
        return applyPagination(pageableWithOrderAt(pageable),
                query -> query
                        .selectFrom(post)
                        .join(post.district, district).fetchJoin()
                        .join(post.category, category).fetchJoin()
                        .where(sellerAndPostStatusCond(seller, postStatuses)));
    }

    private BooleanBuilder defaultCond(List<District> districts){
        return postNotDelete().and(districtIn(districts));
    }

    private BooleanBuilder postStatusCond(List<District> districts, List<PostStatus> postStatuses){
        return defaultCond(districts).and(postStatusIn(postStatuses));
    }

    private BooleanBuilder postStatusAndTitleCond(
            List<District> districts, List<PostStatus> postStatuses, String keyword){
        return defaultCond(districts).and(postStatusIn(postStatuses)).and(titleKeyword(keyword));
    }

    private BooleanBuilder postStatusAndCategoryCond(
            List<District> districts, List<PostStatus> postStatuses, CategoryTag categoryTag){
        return postStatusCond(districts, postStatuses).and(categoryEq(categoryTag));
    }

    private BooleanBuilder sellerAndPostStatusCond(
            Account seller, List<PostStatus> postStatuses){
        return postNotDelete().and(sellerEq(seller)).and(postStatusIn(postStatuses));
    }

    private BooleanBuilder searchCond(PostSearchCondition condition){
        return titleKeyword(condition.getTitle()).and(descKeyword(condition.getDesc()))
                .and(priceGoe(condition.getPriceGoe())).and(priceLoe(condition.getPriceLoe()))
                .and(categoryIn(condition.getCategoryTags())).and(postStatusIn(condition.getPostStatuses()));
    }

    private BooleanBuilder titleKeyword(String keyword){
        return hasText(keyword) ? new BooleanBuilder(post.title.likeIgnoreCase("%" + keyword + "%")) : new BooleanBuilder();
    }

    private BooleanBuilder descKeyword(String keyword){
        return hasText(keyword) ? new BooleanBuilder(post.desc.likeIgnoreCase("%" + keyword + "%")) : new BooleanBuilder();
    }

    private BooleanBuilder priceGoe(Integer priceGoe){
        return nullSafeBuilder(() -> post.price.goe(priceGoe));
    }

    private BooleanBuilder priceLoe(Integer priceLoe){
        return nullSafeBuilder(() -> post.price.loe(priceLoe));
    }


    private BooleanBuilder sellerEq(Account seller){
        return nullSafeBuilder(() -> post.seller.eq(seller));
    }

    private BooleanBuilder postNotDelete(){
        return new BooleanBuilder(post.isDeleted.ne(true));
    }

    private BooleanBuilder districtIn(List<District> districts){
        Assert.isTrue(districts.size() > 0, "");
        return new BooleanBuilder(district.in(districts));
    }

    private BooleanBuilder postStatusIn(List<PostStatus> postStatuses){
        return postStatuses != null && !postStatuses.isEmpty() ? new BooleanBuilder(post.postStatus.in(postStatuses)) : new BooleanBuilder();
    }

    private BooleanBuilder categoryIn(List<CategoryTag> categoryTags){
        return categoryTags != null && !categoryTags.isEmpty() ? new BooleanBuilder(post.category.categoryTag.in(categoryTags)) : new BooleanBuilder();
    }

    private BooleanBuilder categoryEq(CategoryTag categoryTag){
        return nullSafeBuilder(() -> category.categoryTag.eq(categoryTag));
    }

    private BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f){
        try{
            return new BooleanBuilder(f.get());
        }
//        catch (IllegalArgumentException e){
//            return new BooleanBuilder();
//        }
        catch (NullPointerException e){
            return new BooleanBuilder();
        }
    }

    private PageRequest pageableWithOrderAt(Pageable pageable) {
        List<Sort.Order> orders = pageable.getSort().get().collect(Collectors.toList());
        orders.add(new Sort.Order(Sort.Direction.DESC, "orderAt"));
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
    }
}
