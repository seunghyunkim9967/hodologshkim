package com.hodolog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hodolog.api.Repository.UserRepository;
import com.hodolog.api.request.Login;
import com.hodolog.api.request.PostCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("로그인 성공후 세션 1개 생성.")
    void test() throws Exception {

        //given
        Login login = Login.builder()
                .email("dnfheh@naver.com")
                .password("1234")
                .build();


//        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(login);

        System.out.println(json);

        mockMvc.perform(post("/auth/login") // application/json
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
//                .andExpect(content().string("{}"))
                .andDo(print());
        // 테스트 성공 시 db -> post 1개 등록
    }
}