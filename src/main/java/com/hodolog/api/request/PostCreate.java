package com.hodolog.api.request;

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

}
