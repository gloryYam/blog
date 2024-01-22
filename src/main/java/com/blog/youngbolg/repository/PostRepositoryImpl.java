package com.blog.youngbolg.repository;


import com.blog.youngbolg.domain.Post;
import com.blog.youngbolg.request.PostSearchReq;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.blog.youngbolg.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PostSearchReq postSearchReq) {
        return jpaQueryFactory
                .selectFrom(post)
                .limit(postSearchReq.getSize())
                .offset(postSearchReq.getOffset())
                .orderBy(post.id.desc())
                .fetch();
    }
}
