package com.hodolog.api.request;

import com.hodolog.api.domain.Post;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PostSearch {

    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private Integer size = 10;

    @Builder
    public PostSearch(Integer page, Integer size) {
        this.page = page == null ? 1 : page;
        this.size = size == null ? 10 : size;
    }


}
