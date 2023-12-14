package com.nhnacademy.aiot.packet;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;
import java.util.concurrent.BlockingQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WireTest {
    Wire wire;
    Packet packet;

    @BeforeEach
    void setUp() {
        wire = new Wire();
        packet = mock(Packet.class);
    }

    @Test
    @DisplayName("패킷 넣기")
    @SuppressWarnings("unchecked")
    void putTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field queueField = Wire.class.getDeclaredField("queue");
        queueField.setAccessible(true);
        BlockingQueue<Packet> queue = (BlockingQueue<Packet>) queueField.get(wire);

        assertTrue(wire.isEmpty());

        wire.put(null);
        assertTrue(wire.isEmpty());

        wire.put(packet);
        assertAll(
            () -> assertFalse(wire.isEmpty()),
            () -> assertEquals(1, queue.size())
        );
    }

    @Test
    @DisplayName("패킷 꺼내기")
    void takeTest() {
        wire.put(packet);
        assertEquals(packet, wire.take());
    }
}
