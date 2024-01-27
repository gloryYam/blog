package com.blog.youngbolg.service.post;

import com.blog.youngbolg.domain.Post;
import com.blog.youngbolg.domain.PostEditor;
import com.blog.youngbolg.exception.PostNotFound;
import com.blog.youngbolg.repository.post.PostRepository;
import com.blog.youngbolg.request.post.PostCreateReq;
import com.blog.youngbolg.request.post.PostEditReq;
import com.blog.youngbolg.request.post.PostSearchReq;
import com.blog.youngbolg.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post write(PostCreateReq postCreateReq) {
        // postCreate -> Entity
        Post post = Post.builder()
                .title(postCreateReq.getTitle())
                .content(postCreateReq.getContent())
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

    public List<PostResponse> getList(PostSearchReq postSearchReq) {

        return postRepository.getList(postSearchReq).stream()
                .map(PostResponse::new)
                .collect(toList());
    }

    @Transactional
    public PostResponse edit(Long id, PostEditReq postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        PostEditor.PostEditorBuilder postEditorBuilder = postEdit.toEditor();
        Post postEditor = postEditorBuilder.title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

        post.edit(postEditor);

        return new PostResponse(post);
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        // 존재하는 경우
        postRepository.delete(post);
    }
}

















