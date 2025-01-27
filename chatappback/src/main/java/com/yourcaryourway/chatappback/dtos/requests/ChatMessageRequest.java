package com.yourcaryourway.chatappback.dtos.requests;

public class ChatMessageRequest {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ChatMessageRequest(String content) {
        this.content = content;
    }

    public ChatMessageRequest() {
    }
}
