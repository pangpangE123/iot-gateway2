package com.nhnacademy.aiot.node;

import java.util.HashMap;
import java.util.Map;
import com.nhnacademy.aiot.packet.Packet;
import com.nhnacademy.aiot.packet.Wire;

/**
 * 패킷의 전송을 담당하는 노드
 * <p>
 * 패킷을 이전노드에서 받아와 다음 노드에 보내는 책임
 * <p>
 * InputNode와 OutputNode의 역할을 다 합니다
 */
public class InOutputNode extends ActiveNode {

    private final Map<Integer, Port> outPorts = new HashMap<>();
    private final Port inPort;


    public void connect(int portNum, OutputNode outNode) {

        Wire connectWire = outPorts.get(portNum).add(); // 인노드의 아웃포트에 connectWire 달아줌

        outNode.connect(connectWire); // 아웃노드의 인포트에 connectWire 달아줌

    }

    protected void connect(Wire wire) {

        inPort.add(wire);

    }

    protected void send(int portNum, Packet packet) {

        if (outPorts.containsKey(portNum)) {
            outPorts.get(portNum).put(packet);
        }

    }

    protected Packet receive() {

        return inPort.take();

    }
}
