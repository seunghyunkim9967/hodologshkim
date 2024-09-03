package com.hodolog.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hodolog.api.Repository.PostRepository;
import com.hodolog.api.Repository.UserRepository;
import com.hodolog.api.config.HodologMockUser;
import com.hodolog.api.domain.Post;
import com.hodolog.api.domain.Users;
import com.hodolog.api.request.PostCreate;
import com.hodolog.api.request.PostEdit;
import com.hodolog.api.request.Signup;
import com.hodolog.api.response.PostResponse;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    //각각의 메서드 실행 전 repository 초기화
    @BeforeEach
    void clean() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }
//    @Test
//    @DisplayName("/posts 요청시 Hello World를 출력한다.")
//    void test() throws Exception {
        //글 제목
        //글 내용
        // 사용자
            //id
            //name
            //level
        /*
        * Json Data
        * {
        *    "title" : "xxx",
        *    "contetn" : "xxx",
        *    "user" : {
        *               "id": "xxx",
        *               "name: "xxx"
        *             }
        * }
        *
        * */

        // expected
//        mockMvc.perform(post("/posts") // application/json
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param("title", "글 제목입니다.")
//                        .param("content", "글 내용입니다 하하")
//                )dho
//                .andExpect(status().isOk())
//                .andExpect(content().string("Hello World"))
//                .andDo(print());
        @Test
        @DisplayName("글 작성 요청시 Hello World를 출력한다.")
        void test() throws Exception {
        //given
        PostCreate request =  PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();


        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);

        System.out.println(json);

        mockMvc.perform(post("/posts") // application/json
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{}"))
                .andDo(print());
        // 테스트 성공 시 db -> post 1개 등록
    }

//    @Test
//    @DisplayName("/posts 요청시 title 값은 필수다.")
//    void test2() throws Exception {
//
//        mockMvc.perform(post("/posts") // application/json
//                        .contentType(APPLICATION_JSON)
//                // {"title" : ""} OK
//                // {"title" : null} @NotBlank 어노테이션이 같이 체크 해줌
//                        .content("{\"title\": null, \"content\": \"내용입니다.\"}")
//                )
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.code").value("400"))
//                .andExpect(jsonPath("$.massage").value("잘못된 요청입니다."))
//                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주십쇼..."))
//                .andDo(print());
//    }

    @Test
    @HodologMockUser//(name = "홍길동", email = "dnfheh@naver.com", password = "1234") // 기본값으로 넣어놨기 때문에 안넣어도 상관없다.
//    @WithMockUser(username = "dnfheh@naver.com", roles = {"ADMIN", "USER"})
    @DisplayName("글 작성 요청시 DB에 값이 저장된다.")
    void test3() throws Exception {
        // given
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/posts")
                        .header("authorization", "hodolman")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        //given
        Post post = Post.builder()
                .title("1234567890123456")
                .content("bar")
                .build();
        postRepository.save(post);
        // 요구사항
        // Json응답에서 title값 길이 최대 10글자 제한.


        //expected
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("1234567890"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());
        //then
    }

    @Test
    @DisplayName("글 여러개 조회")
    void test5() throws Exception {
        //given
//        Post post1 = Post.builder()
//                .title("title_1")
//                .content("content_1")
//                .build();
//        postRepository.save(post1);
//
//        Post post2 = Post.builder()
//                .title("title_2")
//                .content("content_2")
//                .build();
//        postRepository.save(post2);
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i ->{
                    return Post.builder()
                            .title("호돌맨 제목 - " + i)
                            .content("반포자이 - " + i)
                            .build();
                })
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);
        //expected
                mockMvc.perform(get("/posts?page=1&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
//                .andExpect(jsonPath("$[0].id").value(9))
                .andExpect(jsonPath("$[0].title").value("호돌맨 제목 - 19"))
                .andExpect(jsonPath("$[0].content").value("반포자이 - 19"))
                .andDo(print());

        // 요구사항
        // Json응답에서 title값 길이 최대 10글자 제한.


        //expected
//        mockMvc.perform(get("/posts")
//                        .contentType(APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()", Matchers.is(2)))
//                .andExpect(jsonPath("$[0].id").value(post1.getId()))
//                .andExpect(jsonPath("$[0].title").value("title_1"))
//                .andExpect(jsonPath("$[0].content").value("content_1"))
//                .andExpect(jsonPath("$[1].id").value(post2.getId()))
//                .andExpect(jsonPath("$[1].title").value("title_2"))
//                .andExpect(jsonPath("$[1].content").value("content_2"))
//                .andDo(print());
        //then
    }

    @Test
    @DisplayName("페이지를 0으로 요청하면 첫 페이지를 가져온다.")
    void test6() throws Exception {
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i ->{
                    return Post.builder()
                            .title("호돌맨 제목 - " + i)
                            .content("반포자이 - " + i)
                            .build();
                })
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);
        //expected
        mockMvc.perform(get("/posts?page=0&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].title").value("호돌맨 제목 - 19"))
                .andExpect(jsonPath("$[0].content").value("반포자이 - 19"))
                .andDo(print());

    }

//    @Test
//    @DisplayName("글 제목 수정")
//    void test7() throws Exception {
//        Post post = Post.builder()
//                .title("호돌맨")
//                .content("오들오들맨")
//                .build();
//        postRepository.save(post);
//
//        PostEdit postEdit = PostEdit.builder()
//                .title("호돌건")
//                .content("오돌오돌건초가")
//                .build();
//        //expected
//        mockMvc.perform(patch("/posts/{postId}", post.getId()) // PATCH / posts/ (postId)
//                        .contentType(APPLICATION_JSON)                      )
////                        .content(objectMapper.writeValueAsString(postEdit))
////                )
//                .andExpect(status().isOk())
//                .andDo(print());
//
//    }

    @Test
    @HodologMockUser
    @DisplayName("게시글 삭제")
    void test8() throws Exception {

        Users user = userRepository.findAll().get(0);

        Post post = Post.builder()
                .title("호돌맨")
                .content("오들오들맨")
                .user(user)
                .build();
        postRepository.save(post);

        //expected
        mockMvc.perform(delete("/posts/{postId}", 2)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("존재하지 않는 게시글 조회")
    void test9() throws Exception {
            mockMvc.perform(get("/posts/{postId}", 1L)
                    .contentType(APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andDo(print());
    }

//    @Test
//    @DisplayName("존재하지 않는 게시글 수정")
//    void test10() throws Exception {
//        //given
//        PostEdit postEdit = PostEdit.builder()
//                .title("호돌건")
//                .content("오돌오돌건초가")
//                .build();
//        //expected
//        mockMvc.perform(patch("/posts/{postId}", 1L) // PATCH / posts/ (postId)
//                        .contentType(APPLICATION_JSON))
////                        .content(objectMapper.writeValueAsString(postEdit)))
//                .andExpect(status().isNotFound())
//                .andDo(print());
//
//    }

    @Test
    @DisplayName("게시글 작성시 제목에 'null title'은 포함될 수 없다.")
    void test11() throws Exception {
        //before

        PostCreate request = PostCreate.builder()
                .title("null title입니다")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);


        //when
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    //API문서 생성

    //GET /posts/{postId} -> 단건 조회
    //POST /posts -> 게시글 등록

    //클라이언트 입장 어떤 API 있는지 모름.

    // Spring RestDocs
    // - 운영코드에 -> 영향 ( 테스트 코드로만 작성 가능 )
    // - 코드 수정 -> 문서를 수정 X -> 코드(기능) <-> 문서 ( 문서 작성 후 코드를 수정하지만 문서는 그대로인 경우가 많고 결국 코드와 문서 불일치 발생)
    //  Test 케이스 실행 -> 문서를 생성해준다.

}
