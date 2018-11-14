package com.example.duynguyen.sample.Model;

import java.util.List;

public class MessageChannel {
    List<FriendlyMessage> messages;

    public MessageChannel(List<FriendlyMessage> messages) {
        this.messages = messages;
    }

    public MessageChannel() {
    }

    public List<FriendlyMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<FriendlyMessage> messages) {
        this.messages = messages;
    }
}
