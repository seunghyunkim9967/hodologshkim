package com.hodolog.api.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accessToken;

    @ManyToOne
    private Users users;

    @Builder
    public Session( Users users) {
//        this.id = id;
        this.accessToken = UUID.randomUUID().toString();
        this.users = users;
    }
}
