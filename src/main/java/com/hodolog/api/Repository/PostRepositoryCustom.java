package com.hodolog.api.Repository;


import com.hodolog.api.domain.Post;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(int page);
}
