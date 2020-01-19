package com.jpo.demo;

import com.jpo.demo.interfaces.PlayerInterface;

import java.util.UUID;

public class BasicPlayer implements PlayerInterface {

    private Position position;
    private String playerUUID;
    private String sprite_name;
    private UUID socket_id;
    private int points;


    public BasicPlayer(String playerUUID) {
        this.position = new Position(10,10);
        this.playerUUID = playerUUID;
        this.sprite_name = "";
        this.points = 0;
    }

    public BasicPlayer(String playerUUID, UUID socket_id) {
        this.position = new Position(10,10);
        this.playerUUID = playerUUID;
        this.sprite_name = "";
        this.socket_id = socket_id;
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

    public UUID getSocket_id() {
        return socket_id;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void addPoint(){
        this.points += 1;
    }
}
