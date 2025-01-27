package com.yourcaryourway.chatappback.models;

import com.yourcaryourway.chatappback.entities.Chat;
import jakarta.persistence.*;

import java.util.Date;

public class MessageText {
    private Long id;
    private Date timestamp;
    private String content;
    private Long chatId;

    private Long userId;

    public MessageText(Long id, Date timestamp, String content, Long chatId, Long userId) {
        this.id = id;
        this.timestamp = timestamp;
        this.content = content;
        this.chatId = chatId;
        this.userId = userId;
    }

    public MessageText() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
