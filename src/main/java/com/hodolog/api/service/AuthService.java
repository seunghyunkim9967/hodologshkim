package com.hodolog.api.service;


import com.hodolog.api.Repository.UserRepository;
import com.hodolog.api.crypto.PasswordEncoder;
import com.hodolog.api.domain.Users;
import com.hodolog.api.exception.AlreadyExistsEmailException;
import com.hodolog.api.exception.InvalidSigninInformation;
import com.hodolog.api.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(Signup signup) {
        Optional<Users> usersOptional = userRepository.findByEmail(signup.getEmail());
        if (usersOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }
        //saltLength : saltGenerator = KeyGenerators.secureRandom(saltLength) 랜덤 Byte 생성

        String encryptedPassword = passwordEncoder.encrypt(signup.getPassword());

        var user = Users.builder()
                .name(signup.getName())
                .password(encryptedPassword)
                .email(signup.getEmail())
                .build();

        userRepository.save(user);
    }
}
