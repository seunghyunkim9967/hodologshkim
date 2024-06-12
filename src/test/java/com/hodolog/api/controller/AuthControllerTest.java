package com.hodolog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hodolog.api.Repository.SessionRepository;
import com.hodolog.api.Repository.UserRepository;
import com.hodolog.api.domain.Users;
import com.hodolog.api.request.Login;
import com.hodolog.api.request.PostCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("로그인 성공.")
    void test() throws Exception {

        //given
        userRepository.save(Users.builder()
                        .email("dnfheh78@naver.com")
                        .password("1234")
                .build());
        // Scrypt , Bcrypt

        Login login = Login.builder()
                .email("dnfheh88@naver.com")
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

    @Test
    @Transactional
    @DisplayName("로그인 성공후 세션 1개 생성.")
    void test2() throws Exception {

        //given


        Users user = userRepository.save(Users.builder()
                .email("dnfheh88@naver.com")
                .password("1234")
                .build());
        // Scrypt , Bcrypt

        Login login = Login.builder()
                .email("dnfheh88@naver.com")
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
                .andDo(print());

//        Users loggedInUser = userRepository.findById(user.getId())
//                .orElseThrow(RuntimeException::new);

        Assertions.assertEquals(1L,user.getSessions().size());
    }

    @Test
    @DisplayName("로그인 성공후 세션 응답.")
    void test3() throws Exception {

        //given


        Users user = userRepository.save(Users.builder()
                .email("dnfheh88@naver.com")
                .password("1234")
                .build());
        // Scrypt , Bcrypt

        Login login = Login.builder()
                .email("dnfheh88@naver.com")
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
                .andExpect(jsonPath("$.accessToken",
                        notNullValue()))
                .andDo(print());

//        Users loggedInUser = userRepository.findById(user.getId())
//                .orElseThrow(RuntimeException::new);

//        Assertions.assertEquals(1L,user.getSessions().size());
    }

    @Test
    @DisplayName("로그인 후 권한이 필요한 페이지 접속한다 /foo.")
    void test4() throws Exception {

        //given


        Users user = userRepository.save(Users.builder()
                .email("dnfheh88@naver.com")
                .password("1234")
                .build());
        // Scrypt , Bcrypt

        Login login = Login.builder()
                .email("dnfheh88@naver.com")
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
                .andExpect(jsonPath("$.accessToken",
                        notNullValue()))
                .andDo(print());

//        Users loggedInUser = userRepository.findById(user.getId())
//                .orElseThrow(RuntimeException::new);

//        Assertions.assertEquals(1L,user.getSessions().size());
    }
}