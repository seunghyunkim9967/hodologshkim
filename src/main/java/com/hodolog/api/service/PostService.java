package com.hodolog.api.service;

import com.hodolog.api.Repository.PostRepository;
import com.hodolog.api.domain.Post;
import com.hodolog.api.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

}
