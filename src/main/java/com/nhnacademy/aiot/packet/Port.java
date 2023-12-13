package com.nhnacademy.aiot.packet;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

/*
 * Node와 Wire를 연결해주는 클래스입니다.
 */
@Slf4j
public class Port {
    private final Set<Wire> wires;

    public Port() {
        wires = ConcurrentHashMap.newKeySet();
    }

    /**
     * Port에 Wire를 추가하고 반환합니다.
     * 
     * @return 추가한 Wire
     */
    public Wire add() {
        Wire wire = new Wire();
        wires.add(wire);
        return wire;
    }

    /**
     * Port와 연결된 모든 Wire에 Packet을 보냅니다.
     * 
     * @param packet Wire로 보낼 Packet
     * @return 모든 Wire에 정상적으로 Packet이 보내졌다면 true, 아니면 false
     */
    public synchronized boolean put(Packet packet) {
        boolean isAllPacketSent = true;
        for (Wire wire : wires) {
            if (!wire.put(packet)) {
                log.debug("Port: 패킷이 Wire로 보내지지 않았습니다.");
                isAllPacketSent = false;
            }
        }
        notifyAll();
        return isAllPacketSent;
    }

    /**
     * Port에 연결된 Wire에서 Packet을 하나 꺼냅니다.
     * <p>
     * 연결된 모든 Wire가 비어있다면, 대기 상태로 전환됩니다.
     * 
     * @return Wire에서 꺼낸 Packet
     */
    public synchronized Packet take() {
        while (true) {
            for (Wire wire : wires) {
                if (!wire.isEmpty()) {
                    return wire.take();
                }
            }
            try {
                wait();
                log.debug("Port: 대기 상태로 전환되었습니다.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Port: InterruptedException 발생", e);
            }
        }
    }
}
