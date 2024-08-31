package com.hodolog.api.controller;

import com.hodolog.api.domain.Post;
import com.hodolog.api.exception.InvalidRequest;
import com.hodolog.api.request.PostCreate;
import com.hodolog.api.request.PostEdit;
import com.hodolog.api.request.PostSearch;
import com.hodolog.api.response.PostResponse;
import com.hodolog.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.*;

@Slf4j
@RestController
@RequiredArgsConstructor
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

    private final PostService postService;

    // 유연한 대응 필요
    // 데이터를 검증하는 이유
    // 1. 매번 메서드마다 값을 검증 해야한다.(반복)
    // 검증 부분에서 버그가 발생할 여지가 높음.
    // 2. 응답값에 HashMap -> 응답 클래스를 만들어주는게 좋다.
    // 3. 여러개의 에러처리 힘듬
    // 4. 세 번이상의 반복 작업은 피해야한다.
    //자동화 고려
    // 1. client 개발자가 깜빡할 수 있다. 실수로 값을 안보낼수 있다
    // 2. client bug로 값이 누락될 수 있다.
    // 3. 외부에 해커가 값을 임의로 조작해서 보낼 수 있다.
    // 4. DB에 값을 저장할 때 의도치 않은 오류가 발생할 수 있다.
    // 5. 서버 개발자의 편안함을 위해서
//        if(result.hasErrors()){
//            List<FieldError> fieldErrors = result.getFieldErrors();
//            FieldError firstFieldError = fieldErrors.get(0);
//            String fieldName = firstFieldError.getField();
//            String errorMassage = firstFieldError.getDefaultMessage(); // ..에러메시지
//
//            Map<String, String> error = new HashMap<>();
//            error.put(fieldName, errorMassage);
//            return error;
//
//        }
//        log.info("params={}",params.toString());
    // repository.save(params)
    //postService.write(request) -> Controller -> Service -> Repository -> 최종적으로 넘어온 Json 데이터 값을 Post Entity 형태로 변환하여 저장;

    //        String title = params.getTitle();
//        if(title == null || title.equals("")){
//            //error
//            // 1. 빡세다. (노가다)
//            // 2. 개발팁 -> 무언가 3번이상 반복작업을 할때 내가 뭔가 잘못하고 있는건 아닐지 의심한다.
//            // 3. 누락가능성
//            // 4. 생각보다 검증할게 많다. (꼼꼼하지 않을 수 있다.)
//            // 5. 뭔가 개발자 스럽지 않다. -> 간지 X
//            throw new Exception("타이틀 값이 없어요!");
//        }
//
//        String content = params.getContent();
//
//        if(content == null || content.equals("")){
//            //error
//        }

    /*
    posts -> 글 전체 조회(검색 + 페이징)
            posts/{postId} -> 글 한개만 조회
    */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PreAuthorize("hasRole('ROLE_ADMIN') && #request.title = '하하하' ")
    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {

        //기본적인 요청 인증 값 확인
        //1. GET Parameter -> ??
//        if (authorization.equals("hodolman")) {
//            request.validate();
//            postService.write(request);
//        }
        //2. Header

//        request.validate();
//        postService.write(request);
        request.validate();
        postService.write(request);
    }

    // 조회 API
    // 여러개의 글 API 조회
    // /posts
    //
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        // 응답 클래스를 분리하세요 (서비스 정책에 맞게)
//        PostResponse postResponse = postService.get(id);

        return postService.get(postId);
    }



    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch)  {
        return postService.getList(postSearch);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request, @RequestHeader String authorization)  {
        if (authorization.equals("hodolman")) {
            postService.edit(postId, request);
        }

    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasRole('ROLE_ADMIN') && hasPermission(#postId, 'POST', 'DELETE')")
    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId)  {
            postService.delete(postId);
    }

}
