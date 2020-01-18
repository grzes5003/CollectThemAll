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

    public Position getPlayerPosition(String playerUUID){
        for(BasicPlayer player : basicPlayerArray){
            if(player.getPlayerUUID().equals(playerUUID)){
                return player.getPosition();
            }
        }
        // TODO fix me
        return new Position(-1,-1);
    }

    public Position getEnemyPosition(String my_playerUUID){
        for(BasicPlayer player : basicPlayerArray){
            if(!player.getPlayerUUID().equals(my_playerUUID)){
                return player.getPosition();
            }
        }
        // TODO fix me
        return new Position(-1,-1);
    }

    public void deletePlayer(String playerUUID){
        //TODO fix me
        basicPlayerArray.remove(null);
    }
}
