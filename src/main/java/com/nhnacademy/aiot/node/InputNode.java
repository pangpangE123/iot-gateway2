package com.nhnacademy.aiot.node;

import java.util.HashMap;
import java.util.Map;
import com.nhnacademy.aiot.packet.Packet;
import com.nhnacademy.aiot.packet.Port;
import com.nhnacademy.aiot.packet.Wire;
import lombok.extern.slf4j.Slf4j;

/**
 * 패킷의 전송을 담당하는 노드
 * <p>
 * 패킷을 다음 노드에 보내는 책임
 */
@Slf4j
public abstract class InputNode extends ActiveNode {
    private static final String PORT_NUMBER = "portNumber";

    private final Map<Integer, Port> outPorts = new HashMap<>();

    protected InputNode(NodeProperty nodeProperty) {
        super();
        for (int i = 0; i < nodeProperty.getInt(PORT_NUMBER); i++) {
            outPorts.put(i, new Port());
        }
    }

    /**
     * 어떤 포트의, 어떤 와이어와 연결해줄것인지 결정해주는 메서드 인풋
     * 
     * @param portNum
     * @param outNode
     */
    public void connect(int portNum, OutputNode outNode) {
        if (portNum < 0) {
            throw new IllegalArgumentException("포트넘버는 0이하의 숫자일수 없습니다");
        }

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

        if (packet == null) {
            throw new NullPointerException("패킷이 없는디요");
        }

        if (portNum < 0) {
            throw new IllegalArgumentException("포트넘버는 0이하의 숫자일수 없습니다");
        }

        if (!outPorts.containsKey(portNum)) {
            throw new NullPointerException("해당 포트넘버에는 아웃포트가 없는디요");
        } else {
            outPorts.get(portNum).put(packet);
        }

    }

}
