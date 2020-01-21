package com.jpo.demo;

import com.jpo.demo.dataClasses.Position;
import com.jpo.demo.socketMessages.LevelPlatformsMessage;

import java.util.ArrayList;
import java.util.Random;
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
        /*
        String platform = PlatformGenerator.generatePlatforms(7);
        System.out.println(platform);
        levelPlatformsMessage = new LevelPlatformsMessage(platform);
        */

        String s1 = "0,3|3,1|2,2|7,3|4,4";
        String s2 = "1,1|2,1|6,2|7,2|3,3|4,3|6,4";
        levelPlatformsMessage = new LevelPlatformsMessage(s1);
    }

    public LevelPlatformsMessage getLevelPlatformsMessage() {
        if(levelPlatformsMessage == null) { generateLevel(); }
        return levelPlatformsMessage;
    }

    public int getNumberOfplayers(){
        return basicPlayerArray.size();
    }

    public void addPoint(String playerUUID){
        for(BasicPlayer player : basicPlayerArray){
            if(player.getPlayerUUID().equals(playerUUID)){
                player.addPoint();
            }
        }
    }

    public Position generateStarPosition(){
        Random random = new Random();
        int a = random.nextInt((4 - 1) + 1) + 1;
        //int a = 4;
        switch (a){
            case 1:
                return new Position(100,150);
            case 2:
                return new Position(400,150);
            case 3:
                return new Position(300,500);
            case 4:
                return new Position(600,600);
        }
        return new Position(100,100);
    }
}
