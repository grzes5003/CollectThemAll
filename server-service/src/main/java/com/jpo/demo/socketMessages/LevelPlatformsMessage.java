package com.jpo.demo.socketMessages;

public class LevelPlatformsMessage {

    private String payload;

    public LevelPlatformsMessage() {
    }

    public LevelPlatformsMessage(String payload) {
        super();
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
