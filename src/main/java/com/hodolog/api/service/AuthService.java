package com.hodolog.api.service;


import com.hodolog.api.Repository.UserRepository;
import com.hodolog.api.domain.Session;
import com.hodolog.api.domain.Users;
import com.hodolog.api.exception.InvalidSigninInformation;
import com.hodolog.api.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    //서비스를 직접 호출해서 요청 ( 내용이 달라지면 sideBack 가능성 )
    @Transactional
    public String signin(Login login) {
        Users users = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSigninInformation::new);

        Session session = users.addSession();
        return session.getAccessToken();
    }
}
