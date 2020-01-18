package com.jpo.demo;

import java.util.ArrayList;
import java.util.UUID;

public class GameController {
    private ArrayList<BasicPlayer> basicPlayerArray;

    public GameController() {
        this.basicPlayerArray = new ArrayList<BasicPlayer>();
    }

    public void addPlayer(String playerUUID, UUID socket_id){
        basicPlayerArray.add(new BasicPlayer(playerUUID, socket_id));
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

    public void deletePlayer(UUID socketID){
        //TODO fix me
        int i = 0;
        for(BasicPlayer basicPlayer : basicPlayerArray){
            if (socketID == basicPlayer.getSocket_id()){
                break;
            }
            i++;
        }
        basicPlayerArray.remove(i);
    }

    public void generateLevel(){

    }


}
