package com.jpo.demo.socketMessages;

import com.jpo.demo.dataClasses.Position;
import org.json.JSONException;
import org.json.JSONObject;

public class LocationMessage {

    private String playerUUID;
    private Position position;

    public LocationMessage() {
    }

    public LocationMessage(String playerUUID, String message) {
        super();
        this.playerUUID = playerUUID;

        try {
            final JSONObject obj = new JSONObject(message);
            final JSONObject position = obj.getJSONObject("position");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
