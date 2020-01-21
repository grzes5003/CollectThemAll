package com.jpo.demo;

import com.jpo.demo.dataClasses.Position;
import com.jpo.demo.interfaces.MessageObject;

public class PossitionObject implements MessageObject {

    private String playerUUID;
    private Position position;

    public PossitionObject() {
    }

    public PossitionObject(String playerUUID, Position position) {
        this.playerUUID = playerUUID;
        this.position = position;
    }

    @Override
    public String getPlayerUUID() {
        return playerUUID;
    }

    //TODO fix getMessage
    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public void setMessage(String message) { }

    public Position getPosition(){
        return position;
    }

}
