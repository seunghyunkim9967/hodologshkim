package com.hodolog.api.service;


import com.hodolog.api.Repository.UserRepository;
import com.hodolog.api.domain.User;
import com.hodolog.api.exception.InvalidSigninInformation;
import com.hodolog.api.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public void signin(Login login) {
        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSigninInformation::new);

        user.addSession();
    }
}
