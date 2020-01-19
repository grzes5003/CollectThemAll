package com.jpo.demo;

import com.jpo.demo.socketMessages.LevelPlatformsMessage;

import java.util.ArrayList;
import java.util.UUID;

public class GameController {

    private ArrayList<BasicPlayer> basicPlayerArray;
    private LevelPlatformsMessage levelPlatformsMessage;

    public GameController() {
        this.basicPlayerArray = new ArrayList<BasicPlayer>();
        generateLevel();
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
        levelPlatformsMessage = new LevelPlatformsMessage("1,1|2,1|6,2|7,2|3,3|4,3|6,4");
    }

    public LevelPlatformsMessage getLevelPlatformsMessage() {
        if(levelPlatformsMessage == null) { generateLevel(); }
        return levelPlatformsMessage;
    }

    public int getNumberOfplayers(){
        return basicPlayerArray.size();
    }
}
