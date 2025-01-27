package com.yourcaryourway.chatappback.mappers;

import com.yourcaryourway.chatappback.entities.Chat;
import com.yourcaryourway.chatappback.entities.Message;
import com.yourcaryourway.chatappback.models.MessageText;
import com.yourcaryourway.chatappback.models.MyChat;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChatMapper {
    public List<MyChat> toMyChatList(List<Chat> chatList) {
        return chatList.stream()
                .map(chat -> new MyChat(chat.getId(), chat.getStartDate(), chat.getEndDate(), chat.getStatus(), chat.getUser().getId(), toMessageTextList(chat.getMessages())))
                .collect(Collectors.toList());
    }

    public List<MessageText> toMessageTextList(List<Message> messageList){
        return messageList.stream()
                .map(message -> new MessageText(message.getId(), message.getTimestamp(), message.getContent(), message.getChat().getId(), message.getUser().getId()))
                .collect(Collectors.toList());
    }

    public MyChat toMyChat(Chat chat) {
        return new MyChat(chat.getId(), chat.getStartDate(), chat.getEndDate(), chat.getStatus(), chat.getUser().getId(), toMessageTextList(chat.getMessages()));
    }

    public  MessageText toMessageText (Message message){
        return new MessageText(message.getId(), message.getTimestamp(), message.getContent(), message.getChat().getId(), message.getUser().getId());
    }
}
