package com.blog.youngbolg.repository.post;


import com.blog.youngbolg.request.post.PostSearchRequest;
import com.blog.youngbolg.response.PostResponse;
import com.blog.youngbolg.response.QPostResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.blog.youngbolg.domain.QPost.post;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PostResponse> searchPost(PostSearchRequest postSearchRequest, Pageable pageable) {

        List<PostResponse> postResponses = getPostResponses(postSearchRequest, pageable);

        return PageableExecutionUtils.getPage(postResponses, pageable, totalCount(postSearchRequest)::fetchCount);
    }

    private JPAQuery<Long> totalCount(PostSearchRequest postSearchRequest) {
        return queryFactory
                .select(post.count())
                .from(post)
                .where(
                        containsTitle(postSearchRequest.getTitle()),
                        containsContent(postSearchRequest.getContent())
                );
    }

    private List<PostResponse> getPostResponses(PostSearchRequest postSearchRequest, Pageable pageable) {
        QPostResponse qPostResponse = new QPostResponse(post.id, post.title, post.content);

        return queryFactory
                .select(qPostResponse)
                .from(post)
                .where(
                        containsTitle(postSearchRequest.getTitle()),
                        containsContent(postSearchRequest.getContent())
                )
                .orderBy(post.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression containsTitle(String title) {
        return StringUtils.hasText(title) ? post.title.contains(title) : null;
    }

    private BooleanExpression containsContent(String content) {
        return StringUtils.hasText(content) ? post.content.contains(content) : null;
    }
}
