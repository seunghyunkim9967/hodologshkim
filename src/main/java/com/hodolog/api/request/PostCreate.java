package com.hodolog.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
public class PostCreate {
    @NotBlank(message = "타이틀을 입력해주십쇼...")
    public String title;
    @NotBlank(message = "내용을 입력해주십쇼...")
    public String content;

    public PostCreate() {

    }

    @Builder
    public PostCreate(String content, String title) {
        this.title = title;
        this.content = content;
    }

    //빌더의 장점
    // - 가독성에 좋다. (값 생성에 대한 유연함)
    // - 필요한 값만 받을 수 있다. // -> (오버로딩 가능한 조건 찾아보세요)
    // - 객체의 불변성
    // - ( 순서가 꼬여도 ㄱㅊ. )
}
