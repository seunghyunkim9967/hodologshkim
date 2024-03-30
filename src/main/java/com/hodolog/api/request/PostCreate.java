package com.hodolog.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
public class PostCreate {
    @NotBlank
    public String title;
    @NotBlank
    public String content;

}
