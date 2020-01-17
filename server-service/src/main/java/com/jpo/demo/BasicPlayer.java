package com.jpo.demo;

import com.jpo.demo.interfaces.PlayerInterface;

public class BasicPlayer implements PlayerInterface {

    private Position position;
    private String playerUUID;

    public BasicPlayer(String playerUUID) {
        this.position = new Position(10,10);
        this.playerUUID = playerUUID;
    }
}
