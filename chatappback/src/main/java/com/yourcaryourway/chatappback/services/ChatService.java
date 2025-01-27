package com.yourcaryourway.chatappback.services;

import com.yourcaryourway.chatappback.entities.Chat;
import com.yourcaryourway.chatappback.entities.Message;
import com.yourcaryourway.chatappback.entities.User;
import com.yourcaryourway.chatappback.exceptions.BadRequestExceptionHandler;
import com.yourcaryourway.chatappback.repositories.ChatRepository;
import com.yourcaryourway.chatappback.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessageRepository messageRepository;

    public Chat createChat(Long userId) {
        Chat chat = new Chat();
        chat.setStartDate(new Date());
        chat.setStatus("open");
        // Set user reference
        User user = new User();
        user.setId(userId);
        chat.setUser(user); // Assuming User is preloaded
        return chatRepository.save(chat);
    }

    public List<Chat> getUserChats(Long userId) {
        return chatRepository.findAllByUserId(userId);
    }

    public Chat getUserChatId(Long id){
        return chatRepository.findById(id).orElseThrow(() -> new BadRequestExceptionHandler("Chat not found"));
    }

    public Chat closeChat(Long chatId){
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new BadRequestExceptionHandler("Chat not found"));
        chat.setEndDate(new Date());
        chat.setStatus("closed");
        return chatRepository.save(chat);
    }

    public List<Chat> getAllOpenChats() {
        return chatRepository.findAllByStatus("open");
    }

    public Message saveMessage(Long chatId, String content, User user) {
        Message message = new Message();
        message.setTimestamp(new Date());
        message.setContent(content);
        message.setUser(user);
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new BadRequestExceptionHandler("Chat not found"));
        message.setChat(chat);

        return messageRepository.save(message);
    }
}

