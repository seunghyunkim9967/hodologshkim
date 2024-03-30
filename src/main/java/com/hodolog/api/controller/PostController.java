package com.hodolog.api.controller;

import com.hodolog.api.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
public class PostController {

    // SSR -> jsp, thymeleaf, mustache, freemarker
    // -> html rendering
    // SPA ->
    // vue -> vue+SSR = nuxt
    // react -> react + SSR = next
    // vue -> javascript + < - > API (JSON)

    // Http Method
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    // 글 등록
    // POST Method

    /*@PostMapping("/posts")
    public String post(@RequestParam String title, @RequestParam String content) {

        log.info("title={}, content={}", title, content);
        return "Hello World";
    }*/

    /*@PostMapping("/posts")
    public String post(@RequestParam Map<String, String> params) {

        log.info("params={}",params);
        return "Hello World";
    }*/

    @PostMapping("/posts")
    public String post(@RequestBody @Valid PostCreate params) throws Exception {
        // 데이터를 검증하는 이유

        // 1. client 개발자가 깜빡할 수 있다. 실수로 값을 안보낼수 있다
        // 2. client bug로 값이 누락될 수 있다.
        // 3. 외부에 해커가 값을 임의로 조작해서 보낼 수 있다.
        // 4. DB에 값을 저장할 때 의도치 않은 오류가 발생할 수 있다.
        // 5. 서버 개발자의 편안함을 위해서

        log.info("params={}",params.toString());

        String title = params.getTitle();
        if(title == null || title.equals("")){
            //error
            // 1. 빡세다. (노가다)
            // 2. 개발팁 -> 무언가 3번이상 반복작업을 할때 내가 뭔가 잘못하고 있는건 아닐지 의심한다.
            // 3. 누락가능성
            // 4. 생각보다 검증할게 많다. (꼼꼼하지 않을 수 있다.)
            // 5. 뭔가 개발자 스럽지 않다. -> 간지 X
            throw new Exception("타이틀 값이 없어요!");
        }

        String content = params.getContent();

        if(content == null || content.equals("")){
            //error
        }

        return "Hello World";
    }

}
