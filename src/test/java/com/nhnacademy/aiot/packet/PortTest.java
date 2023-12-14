package com.nhnacademy.aiot.packet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.*;

import java.lang.Thread.State;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PortTest {
    Port port;
    Set<Wire> wires;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        port = new Port();
        Field wiresField = port.getClass().getDeclaredField("wires");
        wiresField.setAccessible(true);
        wires = (Set<Wire>) wiresField.get(port);
    }

    @Test
    @DisplayName("와이어 추가 테스트")
    void addTest() {
        assertTrue(wires.contains(port.add()));
    }

    @Test
    @DisplayName("패킷 추가 테스트")
    void putTest() {
        Packet packet = mock(Packet.class);
        packet.put("key", "value");
        port.add();
        port.add();

        port.put(packet);

        for (Wire wire : wires) {
            assertEquals(packet, wire.take());
        }
    }

    @Test
    @DisplayName("패킷 꺼내기 테스트")
    void takeTest() throws InterruptedException {
        port.add();
        Packet packet = mock(Packet.class);

        AtomicReference<Packet> receivedPacket = new AtomicReference<>();

        Thread thread = new Thread(() -> receivedPacket.set(port.take()));
        thread.start();
        await().until(thread::getState, not(equalTo(State.RUNNABLE)));
        assertEquals(State.WAITING, thread.getState());
        new Thread(() -> port.put(packet)).start();
        await().until(thread::getState, not(equalTo(State.WAITING)));
        assertEquals(packet, receivedPacket.get());
        thread.join();
    }
}
