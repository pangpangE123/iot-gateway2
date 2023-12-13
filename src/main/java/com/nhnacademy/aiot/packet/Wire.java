package com.nhnacademy.aiot.packet;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import lombok.extern.slf4j.Slf4j;

/*
 * Packet을 보내거나 받도록 하는 클래스입니다.
 */
@Slf4j
public class Wire {
    private final BlockingQueue<Packet> queue = new LinkedBlockingQueue<>();

    /**
     * Wire에 Packet을 넣습니다. Packet이 null이라면 Wire에 추가하지 않습니다.
     * 
     * @param packet wire에 넣을 packet
     * @return Packet을 정상적으로 넣었으면 true, 아니면 false
     */
    public boolean put(Packet packet) {
        boolean isPacketSent = false;
        if (packet == null) {
            log.debug("Wire: 패킷이 null이므로 Wire에 추가되지 않았습니다.");
            return true;
        }
        try {
            queue.put(packet);
            isPacketSent = true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Wire: InterruptedException 발생", e);
        }
        return isPacketSent;
    }

    /**
     * Wire에서 packet을 꺼냅니다.
     * 
     * @return Wire에서 정상적으로 Packet을 꺼냈다면 Packet을 반환하고, InterruptedException이
     *         발생하면 null을 반환합니다.
     */
    public Packet take() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Wire: InterruptedException 발생", e);
            return null;
        }
    }

    /**
     * Wire가 비어있는지 확인합니다.
     * 
     * @return Wire가 비어있다면 true, 아니면 false
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
