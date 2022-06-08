package com.woori.demo.controller;

import com.woori.demo.domain.Chat;
import com.woori.demo.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    private ChatService chatService;

    @MessageMapping("/receive")
    @SendTo("/send")
    public Chat chattingHandler(Chat chat){
        return chatService.chattingHandler(chat);
    }
}
