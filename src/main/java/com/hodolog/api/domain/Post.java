package com.hodolog.api.domain;

import com.hodolog.api.request.PostEdit;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String title;

    @Lob
    public String content;

    @ManyToOne
    @JoinColumn
    private Users user;



    @Builder
    public Post(String title, String content, Users user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public String getTitle(String title) {
        // 서비스의 정책을 넣지마세요!!! 절대!!!

        return this.title;
    }

    public PostEditor.PostEditorBuilder toEditor() {
        return PostEditor.builder()
                .title(title)
                .content(content);

    }

    public void edit(PostEditor postEditor) {
        title = postEditor.getTitle();
        content = postEditor.getContent();
    }

    public Long getUserId() {
        return this.user.getId();
    }
}
