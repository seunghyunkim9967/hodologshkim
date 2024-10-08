package com.hodolog.api.service;

import com.hodolog.api.Repository.PostRepository;
import com.hodolog.api.Repository.UserRepository;
import com.hodolog.api.domain.Post;
import com.hodolog.api.domain.PostEditor;
import com.hodolog.api.exception.PostNotFound;
import com.hodolog.api.exception.UserNotFound;
import com.hodolog.api.request.PostCreate;
import com.hodolog.api.request.PostEdit;
import com.hodolog.api.request.PostSearch;
import com.hodolog.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.hodolog.api.domain.QPost.post;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {


    private final UserRepository userRepository;
    private final PostRepository postRepository;



    public void write(Long userId, PostCreate postCreate) {
        var user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);




        // repository.save(postCreate);
        //PostCreate -> Entity
        Post post = Post.builder()
                .user(user)
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();


        postRepository.save(post);
    }

    @SneakyThrows
    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    //글이 너무 많은 경우 -> 비용이 너무 많이 든다.
    // 글이 -> 1억개 -> DB글 모두 조회하는경우 -> DB 뻗을 확률 Up
    // DB -> 애플리케이션 서버로 전달하는 시간, 트래픽비용 등이 많이 발생할 수 있다.

    public List<PostResponse> getList(PostSearch postSearch) {
        // web -> page 1 -> 0
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());

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

    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow();

//        post.setTitle(postEdit.getTitle());
//        post.setContent(postEdit.getContent());
        // post.set ->
        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        PostEditor postEditor = editorBuilder
                .title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();
        System.out.println("postEdit : " + postEditor.getContent());
        post.edit(postEditor);
    }

    public void delete(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        postRepository.delete(post);

    }


}
