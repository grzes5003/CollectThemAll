package com.jpo.demo;

import com.jpo.demo.interfaces.MessageObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public Position getPlayerPositionFromMessage(){
        try {
            final JSONObject obj = new JSONObject(message);
            final JSONObject position = obj.getJSONObject("position");
            return new Position(position.getInt("x"), position.getInt("y"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
