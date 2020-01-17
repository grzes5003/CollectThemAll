package com.jpo.demo;

import java.util.ArrayList;

public class GameController {
    private ArrayList<BasicPlayer> basicPlayerArray;

    public GameController() {
        this.basicPlayerArray = new ArrayList<BasicPlayer>();
    }

    public void addPlayer(String playerUUID){
        basicPlayerArray.add(new BasicPlayer(playerUUID));
    }

    public void setPlayerPosition(String playerUUID, Position position){
        //TODO
    }

    public void deletePlayer(String playerUUID){
        //TODO fix me
        basicPlayerArray.remove(null);
    }
}
