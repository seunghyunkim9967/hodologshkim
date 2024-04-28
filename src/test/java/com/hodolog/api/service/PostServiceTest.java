package com.hodolog.api.service;

import com.hodolog.api.Repository.PostRepository;
import com.hodolog.api.domain.Post;
import com.hodolog.api.request.PostCreate;
import com.hodolog.api.request.PostEdit;
import com.hodolog.api.request.PostSearch;
import com.hodolog.api.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.DESC;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        //given

        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        //when
        postService.write(postCreate);

        //then
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());

    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {
        // given
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();

        postRepository.save(requestPost);

        // when
        PostResponse response = postService.get(requestPost.getId());
        // then
        Assertions.assertNotNull(response);
        assertEquals(1L, postRepository.count());
        assertEquals("foo", response.getTitle());
        assertEquals("bar", response.getContent());

    }

//    @Test
//    @DisplayName("글 여러개 조회")
//    void test3() {
//        // given
//        postRepository.saveAll(List.of(
//                Post.builder()
//                        .title("foo1")
//                        .content("bar1")
//                        .build(),
//                Post.builder()
//                        .title("foo2")
//                        .content("bar2")
//                        .build()
//        ));
//
////        postRepository.save(requestPost2);
//
//        // when
//        List<PostResponse> posts = postService.getList();
//        // then
////        Assertions.assertNotNull(response);
//        assertEquals(2L, posts.size());
//
//    }

    @Test
    @DisplayName("글 1페이지 조회")
    void test3() {
        // given
        //        for(int i = 0; i < 30; i++) {
        //
        //        }
        //  == List<Post> requestPosts = IntStream.range(0, 30)  동일하다
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i ->{
                    return Post.builder()
                            .title("호돌맨 제목 - " + i)
                            .content("반포자이 - " + i)
                            .build();
                })
                        .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

//        Pageable pageable = PageRequest.of(0,5, Sort.by(DESC, "id"));

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .size(10)
                .build();

        // when
        List<PostResponse> posts = postService.getList(postSearch);
        // then
        assertEquals(10L, posts.size());
        assertEquals("호돌맨 제목 - 19", posts.get(0).getTitle());
//        assertEquals("호돌맨 제목 - 4", posts.get(4).getTitle());

    }

    @Test
    @DisplayName("글 제목 수정")
    void test4() {
        Post post = Post.builder()
                            .title("호돌맨")
                            .content("오들오들맨")
                            .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("호돌건")
                .content("오돌오돌건")
                .build();

        // when
        postService.edit(post.getId(), postEdit);
        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));

        Assertions.assertEquals("호돌건", changedPost.getTitle());
    }

    @Test
    @DisplayName("글 내용 수정")
    void test5() {
        Post post = Post.builder()
                .title("호돌맨")
                .content("오들오들맨")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("오돌오돌건초가")
                .build();
        System.out.println(postEdit);
        // when
        postService.edit(post.getId(), postEdit);
        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));
        System.out.println("왜 와이" + changedPost.getContent());
        Assertions.assertEquals("오돌오돌건초가", changedPost.getContent());
    }

    @Test
    @DisplayName("글 내용 수정")
    void test10() {
        Post post = Post.builder()
                .title("호돌맨")
                .content("오들오들맨")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("오돌오돌건초가")
                .build();

        // when
        postService.edit(post.getId(), postEdit);
        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));

        Assertions.assertEquals("오돌오돌건초가", changedPost.getContent());
    }

    @Test
    @DisplayName("게시글 삭제")
    void test6() {
        Post post = Post.builder()
                .title("호돌맨")
                .content("오들오들맨")
                .build();
        postRepository.save(post);

        //when
        postService.delete(post.getId());
        //then
        Assertions.assertEquals(0, postRepository.count());

    }

}