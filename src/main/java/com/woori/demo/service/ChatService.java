package com.woori.demo.service;

import com.woori.demo.domain.Chat;
import com.woori.demo.domain.User;
import com.woori.demo.repository.ChatRepository;
import com.woori.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    public Chat chattingHandler(Chat chat){
        User user=userRepository.findById(chat.getUser().getId()).orElseThrow(() -> new IllegalArgumentException("가입되지 않은 사용자입니다."));
        chat=Chat.builder()
                .user(user)
                .build();
        return chatRepository.save(chat);

    }
}
