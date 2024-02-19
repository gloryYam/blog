package com.blog.youngbolg.repository.post;

import com.blog.youngbolg.request.post.PostSearchReq;
import com.blog.youngbolg.response.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<PostResponse> searchPost(PostSearchReq postSearchReq, Pageable pageable);

}
