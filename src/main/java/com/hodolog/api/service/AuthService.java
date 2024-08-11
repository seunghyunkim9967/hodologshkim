package com.hodolog.api.service;


import com.hodolog.api.Repository.UserRepository;
import com.hodolog.api.domain.Session;
import com.hodolog.api.domain.Users;
import com.hodolog.api.exception.AlreadyExistsEmailException;
import com.hodolog.api.exception.InvalidRequest;
import com.hodolog.api.exception.InvalidSigninInformation;
import com.hodolog.api.request.Login;
import com.hodolog.api.request.Signup;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    //서비스를 직접 호출해서 요청 ( 내용이 달라지면 sideBack 가능성 )
    @Transactional
    public Long signin(Login login) {
//        Users users = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
//                .orElseThrow(InvalidSigninInformation::new);

        Users users = userRepository.findByEmail(login.getEmail())
                .orElseThrow(InvalidSigninInformation::new);

        SCryptPasswordEncoder encoder = new SCryptPasswordEncoder(
                16,
                8,
                1,
                32,
                64);

        //내부적으로 암호화된 Hash값. 복호화 하여 같은 값인지 확인.
        // 사용자의 암호화된 비밀번호의 salt값이 사용자의 비밀번호에 포함. (salt값을 알아내려면 사용자의 정보 조회)
        var matches = encoder.matches(login.getPassword(), users.getPassword());
        if (!matches) {
            throw new InvalidSigninInformation();
        }
//        Session session = users.addSession();
        return users.getId();
    }

    public void signup(Signup signup) {
        Optional<Users> usersOptional = userRepository.findByEmail(signup.getEmail());
        if (usersOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }
        //saltLength : saltGenerator = KeyGenerators.secureRandom(saltLength) 랜덤 Byte 생성
        SCryptPasswordEncoder encoder = new SCryptPasswordEncoder(
                16,
                8,
                1,
                32,
                64);

        String encryptedPassword = encoder.encode(signup.getPassword());

        var user = Users.builder()
                .name(signup.getName())
                .password(encryptedPassword)
                .email(signup.getEmail())
                .build();

        userRepository.save(user);
    }
}
