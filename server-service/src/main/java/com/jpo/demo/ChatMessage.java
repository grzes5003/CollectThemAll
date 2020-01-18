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

/*    public Position getPlayerPositionFromMessage(){
        try {
            JSONObject obj = new JSONObject(message);
            //JSONObject position = obj.getJSONObject("message");
            return new Position((int) obj.getDouble("x"), (int) obj.getDouble("y"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    /*public Position getPlayerPosFromString(){
        String whynot = new String(message);
        if(message.contains("[a-zA-Z]+")){
            return new Position(100,100);
        }
        String[] tmp = whynot.split(",");
        return new Position((int) Double.parseDouble(tmp[0]), (int) Double.parseDouble(tmp[1]));
    }*/

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
