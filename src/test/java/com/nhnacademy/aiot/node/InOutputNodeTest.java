package com.nhnacademy.aiot.node;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import com.nhnacademy.aiot.packet.Packet;
import com.nhnacademy.aiot.packet.Wire;

public class InOutputNodeTest {

    public class InputNodeImple extends InputNode {
        protected InputNodeImple(NodeProperty nodeProperty) {
            super(nodeProperty);
        }

        @Override
        protected void process() {
            // ignore
        }
    }

    public class OutputNodeImple extends OutputNode {
        protected OutputNodeImple() {
            super();
        }

        @Override
        protected void process() {
            // ignore
        }
    }

    InputNode inputNode;
    OutputNode outputNode;

    @BeforeEach
    void reset() {
        NodeProperty mock = mock(NodeProperty.class);
        when(mock.getInt("portNumber")).thenReturn(1);

        inputNode = new InputNodeImple(mock);
        outputNode = new OutputNodeImple();

    }

    @DisplayName("connect : 포트가 음수면 예외 발생")
    @Test
    void throwExceptionIfPortNumberIsMinus() {
        // given
        OutputNode mock = mock(OutputNode.class);

        // when
        Executable executable = () -> inputNode.connect(-1, mock);

        // then
        assertThrows(IllegalArgumentException.class, executable);
    }


    @DisplayName("connect : outNode가 null이면 예외 발생")
    @Test
    void throwExceptionIfOutNodeIsNull() {
        // given
        OutputNode mock = null;

        // when
        Executable executable = () -> inputNode.connect(1, mock);

        // then
        assertThrows(NullPointerException.class, executable);

    }

    @DisplayName("send : 포트가 음수면 예외 발생 ")
    @Test
    void throwExceptionIfPortNumberIsMinus2() {
        // given
        Packet packet = new Packet();
        // when
        Executable executable = () -> inputNode.send(-1, packet);
        // then
        assertThrows(IllegalArgumentException.class, executable);

    }

    @DisplayName("send : InputNode의 outPorts Map에서 입력한 포트넘버에 실제 outPort가 없을경우")
    @Test
    void throwExceptionIfWhereIsMyOutport() {
        // given
        Packet packet = new Packet();

        // when
        Executable executable = () -> inputNode.send(4, packet);

        // then
        assertThrows(NullPointerException.class, executable);
    }

    @DisplayName("send : Packet이 없으면 예외 발생")
    @Test
    void throwExceptionIfWhereIsMyPacket() {
        // given
        Packet packet = null;

        // when
        Executable executable = () -> inputNode.send(0, packet);

        // then
        assertThrows(NullPointerException.class, executable);

    }

    @DisplayName("연결하려고 하는 wire가 없을때")
    @Test
    void throwExceptionIfWhereIsWire() {
        // given
        Wire wire = null;
        // when
        Executable executable = () -> outputNode.connect(wire);
        // then
        assertThrows(NullPointerException.class, executable);
    }
}

