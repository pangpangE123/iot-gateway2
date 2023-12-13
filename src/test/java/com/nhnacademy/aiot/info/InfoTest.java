package com.nhnacademy.aiot.info;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InfoTest {

    Info info;

    @BeforeEach
    void setUp() {
        info = new Info();
    }

    @Test
    @DisplayName("GET 메소드 테스트")
    void testGet() {
        // given
        info.increase(PacketType.SEND);

        // when
        long actual = info.get(PacketType.SEND);

        // then
        assertEquals(1, actual);
    }

    @Test
    @DisplayName("Increase 메소드 테스트")
    void testIncrease() {
        // given
        info.increase(PacketType.SEND);
        info.increase(PacketType.RECEIVE);
        info.increase(PacketType.FAIL);

        // when
        long actual1 = info.get(PacketType.SEND);
        long actual2 = info.get(PacketType.RECEIVE);
        long actual3 = info.get(PacketType.FAIL);

        // then
        assertAll(
                () -> assertEquals(1, actual1),
                () -> assertEquals(1, actual2),
                () -> assertEquals(1, actual3));
    }

    @Test
    @DisplayName("GET 메소드에 키가 NULL일 경우")
    void testGetNullKey() {
        info.increase(PacketType.SEND);

        long actual1 = info.get(null);

        assertEquals(0, actual1);
    }

    @Test
    @DisplayName("Increase 메소드에 키가 NULL일 경우")
    void testIncreaseNullKey() {
        assertThrows(NullPointerException.class, () -> info.increase(null));
    }
}
