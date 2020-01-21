package com.jpo.demo.socketMessages;

import com.jpo.demo.interfaces.MessageObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DefaultMessage implements MessageObject {

    private String playerUUID;
    private String message;

    public DefaultMessage() {
    }

    public DefaultMessage(String playerUUID, String message) {
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
