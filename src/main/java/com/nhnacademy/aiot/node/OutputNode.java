package com.nhnacademy.aiot.node;

/**
 * 패킷의 전송을 담당하는 노드
 * <p>
 * 패킷을 이전 노드에서 받아오는 책임
 */
public abstract class OutputNode extends ActiveNode {

    private final Port inPort;

    /*
     * 이 노드가 가지고 있는 포트에, 특정 wire를 달아줍니다
     */
    protected void connect(Wire wire) {

        inPort.add(wire);

    }

    /*
     * inPort에서 패킷을 꺼내옵니다
     */
    protected Packet receive() {

        return inPort.take();

    }
}
