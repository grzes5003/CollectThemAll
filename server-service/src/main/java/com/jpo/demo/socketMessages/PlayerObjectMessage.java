package com.jpo.demo.socketMessages;

public class PlayerObjectMessage {

    private String playerUUID;
    private String x;
    private String y;
    private String sprite_name;

    public PlayerObjectMessage() {
    }

    public PlayerObjectMessage(String playerUUID, String x, String y, String sprite_name) {
        super();
        this.playerUUID = playerUUID;
        this.x = x;
        this.y = y;
        this.sprite_name = sprite_name;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getSprite_name() {
        return sprite_name;
    }

    public void setPlayerUUID(String playerUUID) {
        this.playerUUID = playerUUID;
    }

    public void setX(String x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
    }

    public void setSprite_name(String sprite_name) {
        this.sprite_name = sprite_name;
    }
}
