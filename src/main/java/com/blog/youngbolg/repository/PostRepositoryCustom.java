package com.blog.youngbolg.repository;

import com.blog.youngbolg.domain.Post;
import com.blog.youngbolg.request.PostSearchReq;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearchReq postSearchReq);

}
