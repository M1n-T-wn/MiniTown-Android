package com.example.minitown.chat.ui.data;

public class ChatModel {
    private String message;
    private String userName;
    private int sender;

    public ChatModel(String message, String myUserName, int messageSenderMe) {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }
}
