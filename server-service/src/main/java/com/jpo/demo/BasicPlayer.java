package com.jpo.demo;

import com.jpo.demo.interfaces.PlayerInterface;

public class BasicPlayer implements PlayerInterface {

    private Position position;
    private String playerUUID;
    private String sprite_name;



    public BasicPlayer(String playerUUID) {
        this.position = new Position(10,10);
        this.playerUUID = playerUUID;
        this.sprite_name = "";
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public Position getPosition() {
        return position;
    }

    public void setSprite_name(String sprite_name) {
        this.sprite_name = sprite_name;
    }

    public String getSprite_name() {
        return sprite_name;
    }
}