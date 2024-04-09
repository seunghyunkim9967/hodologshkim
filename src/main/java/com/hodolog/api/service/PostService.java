package com.hodolog.api.service;

import com.hodolog.api.Repository.PostRepository;
import com.hodolog.api.domain.Post;
import com.hodolog.api.request.PostCreate;
import com.hodolog.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {


    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {
        // repository.save(postCreate);
        //PostCreate -> Entity
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();


        postRepository.save(post);
    }

    @SneakyThrows
    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalAccessException("존재하지 않는 글입니다."));

        PostResponse response = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        return response;
    }

//    @SneakyThrows
//    public PostResponse getRss(Long id) {
//        Post post = postRepository.findById(id)
//                .orElseThrow(() -> new IllegalAccessException("존재하지 않는 글입니다."));
//
//        PostResponse.builder()
//                .id(post.getId())
//                .title(post.getTitle())
//                .content(post.getContent())
//                .build()
//
//
//        return post;
//    }



}
