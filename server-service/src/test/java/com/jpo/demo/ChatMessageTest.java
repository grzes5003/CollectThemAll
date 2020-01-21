package com.jpo.demo;

import com.jpo.demo.socketMessages.DefaultMessage;
import org.junit.jupiter.api.Test;

class ChatMessageTest {

    @Test
    void getPlayerPositionFromMessageTest(){
        String input = "[\"playerMovement\",{\"playerUUID\":\"user919\",\"message\":{\"x\":139.11232581182298,\"y\":194.4741846690447}}]";
        DefaultMessage chatMessage = new DefaultMessage("playerUUID", "{\"x\":139.11232581182298,\"y\":194.4741846690447}" );
        //assertEquals(139, chatMessage.getPlayerPositionFromMessage().x);
    }
}