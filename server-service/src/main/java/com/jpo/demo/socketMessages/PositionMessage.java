package com.jpo.demo.socketMessages;

public class PositionMessage {

    private String playerUUID;
    private String x;
    private String y;

    public PositionMessage() {
    }

    public PositionMessage(String playerUUID, String x, String y) {
        super();
        this.playerUUID = playerUUID;
        this.x = x;
        this.y = y;
    }

    public String getPlayerUUID(){
        return playerUUID;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
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
}
