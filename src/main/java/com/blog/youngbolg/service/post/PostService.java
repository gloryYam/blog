package com.blog.youngbolg.service.post;

import com.blog.youngbolg.domain.Account;
import com.blog.youngbolg.domain.Post;
import com.blog.youngbolg.domain.PostEditor;
import com.blog.youngbolg.exception.PostNotFound;
import com.blog.youngbolg.exception.UserNotFound;
import com.blog.youngbolg.repository.UserRepository;
import com.blog.youngbolg.repository.post.PostRepository;
import com.blog.youngbolg.request.post.PostCreateReq;
import com.blog.youngbolg.request.post.PostEditReq;
import com.blog.youngbolg.request.post.PostSearchReq;
import com.blog.youngbolg.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public Post write(Long userId,PostCreateReq postCreateReq) {
        Account account = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        // postCreate -> Entity
        Post post = Post.builder()
                .title(postCreateReq.getTitle())
                .content(postCreateReq.getContent())
                .account(account)
                .build();

        return postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

    }

    public Page<PostResponse> getList(PostSearchReq postSearchReq, Pageable pageable) {
        return postRepository.searchPost(postSearchReq, pageable);
    }

    @Transactional
    public void edit(Long id, PostEditReq postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        PostEditor postEditor = editorBuilder
                .title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

        post.edit(postEditor);
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        // 존재하는 경우
        postRepository.delete(post);
    }
}

















