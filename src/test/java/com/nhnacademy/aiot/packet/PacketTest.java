package com.nhnacademy.aiot.packet;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PacketTest {
    
    @Test
    @DisplayName("인스턴스 생성")
    void packetTest() {
        Packet packet = new Packet();
        long now = new Date().getTime();
        assertAll(
            () -> assertTrue(packet instanceof JSONObject),
            () -> assertTrue(Math.abs(now - packet.getCreationDate().getTime()) < 1000)
        );
    }
}
