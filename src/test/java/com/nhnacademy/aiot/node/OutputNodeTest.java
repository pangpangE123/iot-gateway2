package com.nhnacademy.aiot.node;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import com.nhnacademy.aiot.packet.Wire;

public class OutputNodeTest {

    public class OutputNodeImple extends OutputNode {
        protected OutputNodeImple() {
            super();
        }

        @Override
        protected void process() {
            // ignore
        }
    }

    OutputNode outputNode;

    @BeforeEach
    void reset() {
        outputNode = new OutputNodeImple();
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
