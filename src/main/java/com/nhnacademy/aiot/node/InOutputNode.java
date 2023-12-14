package com.nhnacademy.aiot.node;

import java.util.HashMap;
import java.util.Map;
import com.nhnacademy.aiot.packet.Packet;
import com.nhnacademy.aiot.packet.Port;
import com.nhnacademy.aiot.packet.Wire;

/**
 * 패킷의 전송을 담당하는 노드
 * <p>
 * 패킷을 이전노드에서 받아와 다음 노드에 보내는 책임
 * <p>
 * InputNode와 OutputNode의 역할을 다 합니다
 */
public abstract class InOutputNode extends ActiveNode {
    private final Map<Integer, Port> outPorts = new HashMap<>();
    private final Port inPort = new Port();

    private InOutputNode(int portNumber) {
        super();
        for (int i = 0; i < portNumber; i++) {
            outPorts.put(i, new Port());
        }
    }

    /**
     * 어떤 포트의, 어떤 와이어와 연결해줄것인지 결정해주는 메서드 인풋
     * 
     * @param portNum 연결할 포트의 번호입니다
     * @param outNode 연결할 노드입니다
     */
    public void connect(int portNum, OutputNode outNode) {
        Wire connectWire = outPorts.get(portNum).add(); // 인노드의 아웃포트에 connectWire 달아줌
        outNode.connect(connectWire); // 아웃노드의 인포트에 connectWire 달아줌

    }

    /**
     * 이 노드가 가지고 있는 포트에, 특정 wire를 달아줍니다
     * 
     * @param wire inPort에 연결될 와이어입니다
     */
    protected void connect(Wire wire) {
        // inPort.add(wire);

    }

    /**
     * 내가 지정한 포트로 패킷을 넘겨주는 메서드
     * 
     * @param portNum 연결할 포트의 번호입니다
     * @param packet 전송할 패킷입니다
     */
    protected void send(int portNum, Packet packet) {
        if (outPorts.containsKey(portNum)) {
            outPorts.get(portNum).put(packet);
        }

    }

    /**
     * inPort에서 패킷을 꺼내옵니다
     */
    protected Packet receive() {
        return inPort.take();

    }
}
