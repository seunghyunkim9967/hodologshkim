package com.hodolog.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hodolog.api.Repository.PostRepository;
import com.hodolog.api.domain.Post;
import com.hodolog.api.request.PostCreate;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;
    
    //각각의 메서드 실행 전 repository 초기화
    @BeforeEach
    void clean() {
        postRepository.deleteAll();
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
        @DisplayName("/posts 요청시 Hello World를 출력한다.")
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

    @Test
    @DisplayName("/posts 요청시 title 값은 필수다.")
    void test2() throws Exception {

        mockMvc.perform(post("/posts") // application/json
                        .contentType(APPLICATION_JSON)
                // {"title" : ""} OK
                // {"title" : null} @NotBlank 어노테이션이 같이 체크 해줌
                        .content("{\"title\": null, \"content\": \"내용입니다.\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.massage").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주십쇼..."))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 DB에 값이 저장된다.")
    void test3() throws Exception {
    //before

    //when
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content("{\"title\": \"제목입니다.\", \"content\": \"내용입니다.\"}")
                )
                .andExpect(status().isOk())
                .andDo(print());
        // db -> post 1개 등록 -> 총 2개 test1, test3 pass
        //then
        //고로 다른 테스트들이 영향 끼치지 않게 만들어 줘야함.
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
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i ->{
                    return Post.builder()
                            .title("호돌맨 제목 - " + i)
                            .content("반포자이 - " + i)
                            .build();
                })
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);
        //expected
                mockMvc.perform(get("/posts?page=1&sort=id,desc")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(5)))
                .andExpect(jsonPath("$[0].id").value(30))
                .andExpect(jsonPath("$[0].title").value("호돌맨 제목 - 30"))
                .andExpect(jsonPath("$[0].content").value("반포자이 - 30"))
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


}
