package com.nhnacademy.aiot.node;

import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.Port;

/**
 * 패킷의 전송을 담당하는 노드
 * <p>
 * 패킷을 InOutputNode로 전송해주는 역할
 */
public abstract class InputNode extends ActiveNode {

    private final Map<Integer, Port> outPort = new HashMap<>();

    /**
     * 어떤 포트의, 어떤 와이어와 연결해줄것인지 결정해주는 메서드
     * 
     * @param portNum
     * @param wire
     */
    private void connectPort(int portNum, Wire wire) {
        outPort.get(portNum).add(wire);
    }

    /**
     * 내가 지정한 포트로 패킷을 넘겨주는 메서드
     * 
     * @param portNum
     * @param packet
     */
    public void sendPacket(int portNum, Packet packet) {

        if (outPort.containsKey(portNum)) {
            outPort.get(portNum).put(packet);
        }
    }



}
