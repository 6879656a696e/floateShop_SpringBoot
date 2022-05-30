package com.woori.demo.service;

import com.woori.demo.domain.Authority;
import com.woori.demo.domain.User;
import com.woori.demo.dto.UserDto;
import com.woori.demo.exception.ResourceNotFoundException;
import com.woori.demo.repository.UserRepository;
import com.woori.demo.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.Collections;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(UserDto dto) {
        Authority authority= Authority.builder()
                .authorityName("ROLE_USER")
                .build();
        User user=User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .address1(dto.getAddress1())
                .address2(dto.getAddress2())
                .zipcode(dto.getZipcode())
                .activated(true)
                .createdAt(LocalDateTime.now())
                .authorities(Collections.singleton(authority))
                .build();

        return userRepository.save(user);
    }

    //이메일 중복체크
    @Transactional
    public boolean existUser(String email){
        return userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String email) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByEmail(email).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail).orElse(null));
    }

    //회원정보 가져오기
    public UserDto getInfo(String email){
        return  UserDto.from(userRepository.findOneByEmail(email).orElse(null));
    }

    public User findUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return user;
    }
}
