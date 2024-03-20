package com.blog.youngbolg.service.post;

import com.blog.youngbolg.domain.User;
import com.blog.youngbolg.domain.Post;
import com.blog.youngbolg.domain.PostEditor;
import com.blog.youngbolg.exception.PostNotFound;
import com.blog.youngbolg.exception.UserNotFound;
import com.blog.youngbolg.repository.UserRepository;
import com.blog.youngbolg.repository.post.PostRepository;
import com.blog.youngbolg.request.post.PostEditRequest;
import com.blog.youngbolg.request.post.PostSearchRequest;
import com.blog.youngbolg.response.PostResponse;
import com.blog.youngbolg.service.post.request.PostCreateServiceRequest;
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

    /**
     * 작성
     */
    public Post write(Long userId, PostCreateServiceRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        Post result = request.toEntity();
        Post post = Post.of(result, user);

        return postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        return PostResponse.of(post);

    }

    public Page<PostResponse> getList(PostSearchRequest postSearchRequest, Pageable pageable) {
        return postRepository.searchPost(postSearchRequest, pageable);
    }

    @Transactional
    public void edit(Long id, PostEditRequest postEdit) {
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

















