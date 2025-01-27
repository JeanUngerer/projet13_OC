package com.yourcaryourway.chatappback.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class ChatMessage {
    private Long chatId;
    private Long userId;
    private String content;
    private boolean sentByUser;

    public ChatMessage(Long chatId, Long userId, String content, boolean sentByUser) {
        this.chatId = chatId;
        this.userId = userId;
        this.content = content;
        this.sentByUser = sentByUser;
    }

    public ChatMessage() {
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSentByUser() {
        return sentByUser;
    }

    public void setSentByUser(boolean sentByUser) {
        this.sentByUser = sentByUser;
    }
}

