package com.example.duynguyen.sample.Model;

import java.util.List;

public class MessageChannel {
    private String channelType ;


    List<FriendlyMessage> messages;

    public MessageChannel(String channelType,List<FriendlyMessage> messages) {
        this.messages = messages;
        this.channelType = channelType;
    }

    public MessageChannel() {
    }

    public List<FriendlyMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<FriendlyMessage> messages) {
        this.messages = messages;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
}
