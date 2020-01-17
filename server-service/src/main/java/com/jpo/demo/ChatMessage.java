package com.jpo.demo;

import com.jpo.demo.interfaces.MessageObject;

public class ChatMessage implements MessageObject {

    private String playerUUID;
    private String message;

    public ChatMessage() {
    }

    public ChatMessage(String playerUUID, String message) {
        super();
        this.playerUUID = playerUUID;
        this.message = message;
    }

    public void setUserName(String userName) {
        this.playerUUID = userName;
    }

    public String getPlayerUUID(){
        return playerUUID;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
