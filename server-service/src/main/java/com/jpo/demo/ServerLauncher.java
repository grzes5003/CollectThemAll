package com.jpo.demo;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

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
                gameController.addPlayer(data.getPlayerUUID());
            }
        });

        server.addEventListener("requestPosition", ChatMessage.class, new DataListener<ChatMessage>() {
            @Override
            public void onData(SocketIOClient client, ChatMessage data, AckRequest ackRequest) {
                System.out.println("players pos requested: " + data.getPlayerUUID());
                gameController.getPlayerPosition(data.getPlayerUUID());
            }
        });

        server.addEventListener("playerMovement", ChatMessage.class, new DataListener<ChatMessage>() {
            @Override
            public void onData(SocketIOClient client, ChatMessage data, AckRequest ackRequest) {
                //System.out.println("players pos requested: " + data.getPlayerUUID());
                gameController.setPlayerPosition(data.getPlayerUUID(), data.getPlayerPositionFromMessage());
            }
        });


        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }
}
