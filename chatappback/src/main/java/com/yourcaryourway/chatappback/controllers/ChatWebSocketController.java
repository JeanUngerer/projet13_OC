package com.yourcaryourway.chatappback.controllers;

import com.yourcaryourway.chatappback.models.MyChat;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;

@Controller
public class ChatWebSocketController {

    @MessageMapping("/send-message")
    @SendTo("/topic/messages/{chatId}")
    public TextMessage sendMessage(TextMessage chatMessage) {
        // Broadcast message to topic
        System.out.println(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/new-chat")
    @SendTo("/topic/new-chat")
    public MyChat newChat(MyChat myChat) {
        // Broadcast message to topic
        System.out.println(myChat);
        return myChat;
    }

    @MessageMapping("/close-chat")
    @SendTo("/topic/close-chat")
    public MyChat closeChat(MyChat myChat) {
        // Broadcast message to topic
        System.out.println(myChat);
        return myChat;
    }
}

