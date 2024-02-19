package com.blog.youngbolg.service.post;

import com.blog.youngbolg.domain.Post;
import com.blog.youngbolg.request.post.PostEditReq;
import com.blog.youngbolg.request.post.PostSearchReq;
import com.blog.youngbolg.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostFacade {

    private final PostService postService;


    public Page<PostResponse> findPosts(PostSearchReq postSearchReq, Pageable pageable){
        return postService.getList(postSearchReq, pageable);
    }


    private Post toEntity(PostEditReq request) {
        return Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }
}
