package com.nhnacademy.aiot.node;

import java.util.HashMap;
import java.util.Map;
import com.nhnacademy.aiot.packet.Packet;
import com.nhnacademy.aiot.packet.Wire;

/**
 * 패킷의 전송을 담당하는 노드
 * <p>
 * 패킷을 다음 노드에 보내는 책임
 */
public abstract class InputNode extends ActiveNode {

    private final Map<Integer, Port> outPorts = new HashMap<>();

    /**
     * 어떤 포트의, 어떤 와이어와 연결해줄것인지 결정해주는 메서드 인풋
     * 
     * @param portNum
     * @param outNode
     */
    public void connect(int portNum, OutputNode outNode) {

        Wire connectWire = outPorts.get(portNum).add(); // 인노드의 아웃포트에 connectWire 달아줌

        outNode.connect(connectWire); // 아웃노드의 인포트에 connectWire 달아줌
    }

    /**
     * 내가 지정한 포트로 패킷을 넘겨주는 메서드
     *
     * @param portNum
     * @param packet
     */
    protected void send(int portNum, Packet packet) {

        if (outPorts.containsKey(portNum)) {
            outPorts.get(portNum).put(packet);
        }

    }

}
