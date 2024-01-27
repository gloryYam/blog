package com.blog.youngbolg.repository.post;

import com.blog.youngbolg.domain.Post;
import com.blog.youngbolg.request.post.PostSearchReq;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearchReq postSearchReq);

}
