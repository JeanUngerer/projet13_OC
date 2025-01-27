package com.yourcaryourway.chatappback.models;

import com.yourcaryourway.chatappback.entities.Message;
import com.yourcaryourway.chatappback.entities.User;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

public class MyChat {

    private Long id;

    private Date startDate;

    private Date endDate;

    private String status; // Ex : "open", "closed", "pending"

    private Long userId;

    private List<MessageText> messages;

    public MyChat(Long id, Date startDate, Date endDate, String status, Long userId, List<MessageText> messages) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.userId = userId;
        this.messages = messages;
    }

    public MyChat() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<MessageText> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageText> messages) {
        this.messages = messages;
    }
}
