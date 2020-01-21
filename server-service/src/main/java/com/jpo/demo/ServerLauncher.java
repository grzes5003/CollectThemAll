package com.jpo.demo;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.jpo.demo.dataClasses.GeneratorConstatnts;
import com.jpo.demo.dataClasses.Position;
import com.jpo.demo.socketMessages.DefaultMessage;
import com.jpo.demo.socketMessages.PlayerObjectMessage;
import com.jpo.demo.socketMessages.PositionMessage;

public class ServerLauncher {
    public static void main(String[] args) throws InterruptedException {

        final GameController gameController = new GameController();

        Configuration config = new Configuration();
        config.setHostname(GeneratorConstatnts.SERVER_IP_NUMBER);
        config.setPort(GeneratorConstatnts.PORT_NUMBER);

        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("chatevent", DefaultMessage.class, new DataListener<DefaultMessage>() {
            @Override
            public void onData(SocketIOClient client, DefaultMessage data, AckRequest ackRequest) {
                // broadcast messages to all clients
                server.getBroadcastOperations().sendEvent("chatevent", data);
            }
        });

        server.addEventListener("mine_event", DefaultMessage.class, new DataListener<DefaultMessage>() {
            @Override
            public void onData(SocketIOClient client, DefaultMessage data, AckRequest ackRequest) {
                System.out.println("custom event");
            }
        });

        server.addEventListener("newPlayer", DefaultMessage.class, new DataListener<DefaultMessage>() {
            @Override
            public void onData(SocketIOClient client, DefaultMessage data, AckRequest ackRequest) {
                System.out.println("player added: " + data.getPlayerUUID());
                if(gameController.getNumberOfplayers() == 0){
                    server.getBroadcastOperations().sendEvent("newStar", new PositionMessage("star","100","100"));
                }
                gameController.addPlayer(data.getPlayerUUID(), client.getSessionId());
                server.getBroadcastOperations().sendEvent("newEnemyPlayer", data);
            }
        });

        server.addEventListener("requestPosition", DefaultMessage.class, new DataListener<DefaultMessage>() {
            @Override
            public void onData(SocketIOClient client, DefaultMessage data, AckRequest ackRequest) {
                System.out.println("players pos requested: " + data.getPlayerUUID());
                gameController.getPlayerPosition(data.getPlayerUUID());
            }
        });

        server.addEventListener("playerMovement", PositionMessage.class, new DataListener<PositionMessage>() {
            @Override
            public void onData(SocketIOClient client, PositionMessage data, AckRequest ackRequest) {
                //System.out.println("player moved: " + data.getPlayerUUID());
                gameController.setPlayerPosition(data.getPlayerUUID(), new Position(Float.parseFloat(data.getX()), Float.parseFloat(data.getY())));
                server.getBroadcastOperations().sendEvent("playerMovementResp", data);
            }
        });

        server.addEventListener("enemyPlayerDataReq", PlayerObjectMessage.class, new DataListener<PlayerObjectMessage>() {
            @Override
            public void onData(SocketIOClient client, PlayerObjectMessage data, AckRequest ackRequest) {
                System.out.println("enemy object requested: " + data.getPlayerUUID());
                Position pos = gameController.getEnemyPosition(data.getPlayerUUID());
                System.out.println("enemy position : " + pos.x);
                server.getBroadcastOperations().sendEvent("enemyPlayerDataResp",
                        new PlayerObjectMessage(data.getPlayerUUID(),
                                String.valueOf(pos.x), String.valueOf(pos.y),
                                "otherPlayer"));
            }
        });

        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient socketIOClient) {
                gameController.deletePlayer(socketIOClient.getSessionId());
                server.getBroadcastOperations().sendEvent("enemyPlayerDisconnect");
            }
        });

        server.addEventListener("levelLayoutReq", DefaultMessage.class, new DataListener<DefaultMessage>() {
            @Override
            public void onData(SocketIOClient client, DefaultMessage data, AckRequest ackRequest) {
                server.getBroadcastOperations().sendEvent("levelLayoutResp", gameController.getLevelPlatformsMessage());
            }
        });

        server.addEventListener("starCollected", DefaultMessage.class, new DataListener<DefaultMessage>() {
            @Override
            public void onData(SocketIOClient client, DefaultMessage data, AckRequest ackRequest) {
                System.out.println("Star collected by: " + data.getPlayerUUID());
                // add point
                gameController.addPoint(data.getPlayerUUID());
                // generate new star
                Position pos = gameController.generateStarPosition();

                server.getBroadcastOperations().sendEvent("newStar", new PositionMessage( data.getPlayerUUID(), String.valueOf(pos.x), String.valueOf(pos.y)));
            }
        });

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }
}
