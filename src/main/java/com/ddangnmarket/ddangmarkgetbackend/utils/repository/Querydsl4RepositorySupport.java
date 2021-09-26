package com.ddangnmarket.ddangmarkgetbackend.utils.repository;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ddangnmarket.ddangmarkgetbackend.domain.QPost.post;
import static java.util.stream.Collectors.*;

/**
 * @author SeunghyunYoo
 */
@Repository
public abstract class Querydsl4RepositorySupport {
    private final Class domainClass;
    private Querydsl querydsl;
    private EntityManager entityManager;
    private JPAQueryFactory queryFactory;

    public Querydsl4RepositorySupport(Class<?> domainClass) {
        Assert.notNull(domainClass, "Domain class must not be null!");
        this.domainClass = domainClass;
    }

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        Assert.notNull(entityManager, "EntityManager must not be null!");
        JpaEntityInformation entityInformation =
                JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager);
        SimpleEntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;
        EntityPath path = resolver.createPath(entityInformation.getJavaType());
        this.entityManager = entityManager;
        this.querydsl = new Querydsl(entityManager, new
                PathBuilder<>(path.getType(), path.getMetadata()));
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @PostConstruct
    public void validate() {
        Assert.notNull(entityManager, "EntityManager must not be null!");
        Assert.notNull(querydsl, "Querydsl must not be null!");
        Assert.notNull(queryFactory, "QueryFactory must not be null!");
    }

    protected JPAQueryFactory getQueryFactory() {
        return queryFactory;
    }
    protected Querydsl getQuerydsl() {
        return querydsl;
    }
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected <T> JPQLQuery<T> select(Expression<T> expr){
        return getQueryFactory().select(expr);
    }
    protected <T> JPAQuery<T> selectFrom(EntityPath<T> from){
        return getQueryFactory().selectFrom(from);
    }

    protected <T> Page<T> applyPagination(Pageable pageable, Function<JPAQueryFactory, JPQLQuery> contentQuery){
        checkSortProperties(pageable);

        JPQLQuery jpaQuery = contentQuery.apply(queryFactory);
        List<T> content = getQuerydsl().applyPagination(pageable, jpaQuery).fetch();
        return PageableExecutionUtils.getPage(content, pageable, jpaQuery::fetchCount);
    }
    protected <T> Page<T> applyPagination(Pageable pageable, Function<JPAQueryFactory, JPQLQuery> contentQuery,
                                          Function<JPAQueryFactory, JPQLQuery> countQuery){
        checkSortProperties(pageable);

        JPQLQuery jpaContentQuery = contentQuery.apply(queryFactory);
        JPQLQuery jpaCountQuery = countQuery.apply(queryFactory);
        List<T> content = getQuerydsl().applyPagination(pageable, jpaContentQuery).fetch();
        return PageableExecutionUtils.getPage(content, pageable, jpaCountQuery::fetchCount);
    }

    private void checkSortProperties(Pageable pageable) {
        List<String> domainFields = Arrays.stream(domainClass.getDeclaredFields()).map(Field::getName).collect(toList());
        List<String> sortFields = pageable.getSort().get().map(Sort.Order::getProperty).collect(toList());
        if (!domainFields.containsAll(sortFields)) {
            throw new IllegalSortArgumentException("invalid sort property, properties must be in " + domainFields);
        }
    }
}
