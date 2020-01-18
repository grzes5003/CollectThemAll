package com.jpo.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatMessageTest {

    @Test
    void getPlayerPositionFromMessageTest(){
        String input = "[\"playerMovement\",{\"playerUUID\":\"user919\",\"message\":{\"x\":139.11232581182298,\"y\":194.4741846690447}}]";
        ChatMessage chatMessage = new ChatMessage("playerUUID", "{\"x\":139.11232581182298,\"y\":194.4741846690447}" );
        //assertEquals(139, chatMessage.getPlayerPositionFromMessage().x);
    }
}