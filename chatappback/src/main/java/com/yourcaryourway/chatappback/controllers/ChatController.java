package com.yourcaryourway.chatappback.controllers;

import com.yourcaryourway.chatappback.dtos.requests.ChatMessageRequest;
import com.yourcaryourway.chatappback.entities.Chat;
import com.yourcaryourway.chatappback.entities.Message;
import com.yourcaryourway.chatappback.entities.User;
import com.yourcaryourway.chatappback.mappers.ChatMapper;
import com.yourcaryourway.chatappback.models.MessageText;
import com.yourcaryourway.chatappback.models.MyChat;
import com.yourcaryourway.chatappback.services.ChatService;
import com.yourcaryourway.chatappback.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatMapper chatMapper;

    @PostMapping("/newchat")
    public ResponseEntity<MyChat> createChat(Authentication authentication) {
        User user = userService.findUserByEmail(authentication.getName());
        Chat chat = chatService.createChat(user.getId());
        chat.setMessages(new ArrayList<>());
        messagingTemplate.convertAndSend("/topic/new-chat", chatMapper.toMyChat(chat));
        return ResponseEntity.ok(chatMapper.toMyChat(chat));
    }

    @GetMapping("mychats")
    public ResponseEntity<List<MyChat>> getUserChats(Authentication authentication) {
        User user = userService.findUserByEmail(authentication.getName());
        return ResponseEntity.ok(chatMapper.toMyChatList(chatService.getUserChats(user.getId())));
    }

    @GetMapping("mychat/{id}")
    public ResponseEntity<MyChat> getUserChatId(Authentication authentication, @PathVariable("id") Long id) {
        return ResponseEntity.ok(chatMapper.toMyChat(chatService.getUserChatId(id)));
    }

    @GetMapping("openchats")
    public ResponseEntity<List<MyChat>> getOpenChats(Authentication authentication) {
        User user = userService.findUserByEmail(authentication.getName());
        return ResponseEntity.ok(chatMapper.toMyChatList(chatService.getAllOpenChats()));
    }

    @PutMapping("close/{id}")
    public ResponseEntity<MyChat> closeChat(Authentication authentication, @PathVariable("id") Long id) {
        User user = userService.findUserByEmail(authentication.getName());

        MyChat myClosedChat = chatMapper.toMyChat(chatService.closeChat(id));
        messagingTemplate.convertAndSend("/topic/close-chat", myClosedChat);
        return ResponseEntity.ok(myClosedChat);
    }

    @PostMapping("/{chatId}/messages")
    public ResponseEntity<MessageText> sendMessage(
            Authentication authentication,
            @PathVariable Long chatId,
            @RequestBody ChatMessageRequest chatMessage) {

        User user = userService.findUserByEmail(authentication.getName());
        Message message = chatService.saveMessage(chatId, chatMessage.getContent(), user);
        // Broadcast message via WebSocket
        messagingTemplate.convertAndSend("/topic/messages/" + chatId, new MessageText(message.getId(), message.getTimestamp(), chatMessage.getContent(), chatId, user.getId() ));
        return ResponseEntity.ok(chatMapper.toMessageText(message));
    }
}
