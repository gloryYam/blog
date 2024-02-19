package com.blog.youngbolg.repository.post;


import com.blog.youngbolg.request.post.PostSearchReq;
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
    public Page<PostResponse> searchPost(PostSearchReq postSearchReq, Pageable pageable) {

        List<PostResponse> postResponses = getPostResponses(postSearchReq, pageable);

        return PageableExecutionUtils.getPage(postResponses, pageable, totalCount(postSearchReq)::fetchCount);
    }

    private JPAQuery<Long> totalCount(PostSearchReq postSearchReq) {
        return queryFactory
                .select(post.count())
                .from(post)
                .where(
                        containsTitle(postSearchReq.getTitle()),
                        containsContent(postSearchReq.getContent())
                );
    }

    private List<PostResponse> getPostResponses(PostSearchReq postSearchReq, Pageable pageable) {
        QPostResponse qPostResponse = new QPostResponse(post.id, post.title, post.content);

        return queryFactory
                .select(qPostResponse)
                .from(post)
                .where(
                        containsTitle(postSearchReq.getTitle()),
                        containsContent(postSearchReq.getContent())
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
