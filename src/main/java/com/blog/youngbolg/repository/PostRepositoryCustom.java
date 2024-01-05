package com.blog.youngbolg.repository;

import com.blog.youngbolg.domain.Post;
import com.blog.youngbolg.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);

}
