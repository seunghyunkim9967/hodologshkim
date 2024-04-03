package com.hodolog.controller;


import com.hodolog.api.Repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;
    @Test
    @DisplayName("/posts 요청시 Hello World를 출력한다.")
    void test() throws Exception {
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

        mockMvc.perform(post("/posts") // application/json
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"제목입니다.\", \"content\": \"내용입니다.\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{}"))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 title 값은 필수다.")
    void test2() throws Exception {

        mockMvc.perform(post("/posts") // application/json
                        .contentType(MediaType.APPLICATION_JSON)
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
    //when
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"제목입니다.\", \"content\": \"내용입니다.\"}")
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    //then

    assertEquals(1L, postRepository.count());

}
