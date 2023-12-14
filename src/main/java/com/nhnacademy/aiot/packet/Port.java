package com.nhnacademy.aiot.packet;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

/*
 * Node와 Wire를 연결해주는 클래스입니다.
 */
@Slf4j
public class Port {

    private static final String FAILED_TO_PUT_PACKET_MESSAGE = "Port: 패킷이 Wire로 보내지지 않았습니다.";
    private static final String WAITING_STRATED_MESSAGE = "Port: 대기 상태로 전환되었습니다.";
    private static final String WAITING_ENDED_MESSAGE = "Port: 대기 상태가 해제되었습니다.";
    private static final String INTERRUPTED_EXCEPTION_MESSAGE = "Port: InterruptedException 발생";
    private static final String EXIT_DUE_TO_EXCEPTION_MESSAGE = "Port: interrupt 신호를 받아 종료되었습니다.";

    private final Set<Wire> wires;

    public Port() {
        wires = ConcurrentHashMap.newKeySet();
    }

    /**
     * Port에 새로운 Wire를 추가하고 반환합니다.
     * 
     * @return 추가한 Wire
     */
    public Wire add() {
        Wire wire = new Wire();
        wires.add(wire);
        return wire;
    }

    /**
     * Port에 Wire를 추가합니다.
     * 
     * @param wire 추가할 Wire
     */
    public void add(Wire wire) {
        wires.add(wire);
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
                log.debug(FAILED_TO_PUT_PACKET_MESSAGE);
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
        while (!Thread.currentThread().isInterrupted()) {
            for (Wire wire : wires) {
                if (!wire.isEmpty()) {
                    return wire.take();
                }
            }
            try {
                log.debug(WAITING_STRATED_MESSAGE);
                wait();
                log.debug(WAITING_ENDED_MESSAGE);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error(INTERRUPTED_EXCEPTION_MESSAGE, e);
            }
        }
        log.debug(EXIT_DUE_TO_EXCEPTION_MESSAGE);
        return null;
    }
}
