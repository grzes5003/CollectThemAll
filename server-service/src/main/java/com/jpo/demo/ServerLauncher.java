package com.jpo.demo;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.jpo.demo.socketMessages.LevelPlatformsMessage;
import com.jpo.demo.socketMessages.PlayerObjectMessage;
import com.jpo.demo.socketMessages.PositionMessage;

public class ServerLauncher {
    public static void main(String[] args) throws InterruptedException {

        final GameController gameController = new GameController();

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("chatevent", ChatMessage.class, new DataListener<ChatMessage>() {
            @Override
            public void onData(SocketIOClient client, ChatMessage data, AckRequest ackRequest) {
                // broadcast messages to all clients
                server.getBroadcastOperations().sendEvent("chatevent", data);
            }
        });

        server.addEventListener("mine_event", ChatMessage.class, new DataListener<ChatMessage>() {
            @Override
            public void onData(SocketIOClient client, ChatMessage data, AckRequest ackRequest) {
                System.out.println("custom event");
            }
        });

        server.addEventListener("newPlayer", ChatMessage.class, new DataListener<ChatMessage>() {
            @Override
            public void onData(SocketIOClient client, ChatMessage data, AckRequest ackRequest) {
                System.out.println("player added: " + data.getPlayerUUID());
                gameController.addPlayer(data.getPlayerUUID(), client.getSessionId());
                server.getBroadcastOperations().sendEvent("newEnemyPlayer", data);
            }
        });

        server.addEventListener("requestPosition", ChatMessage.class, new DataListener<ChatMessage>() {
            @Override
            public void onData(SocketIOClient client, ChatMessage data, AckRequest ackRequest) {
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

        server.addEventListener("levelLayoutReq", ChatMessage.class, new DataListener<ChatMessage>() {
            @Override
            public void onData(SocketIOClient client, ChatMessage data, AckRequest ackRequest) {
                server.getBroadcastOperations().sendEvent("levelLayoutResp", gameController.getLevelPlatformsMessage());
            }
        });

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }
}
