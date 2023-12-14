package com.nhnacademy.aiot.repository;

import java.lang.reflect.Field;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.nhnacademy.aiot.repository.Data.DataBuilder;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DataTest {

    private static final DataBuilder DEFAULT_DATA_BUILDER = //
            Data.builder() //
                    .deviceEui("deviceEui") //
                    .site("site") //
                    .branch("branch") //
                    .place("place") //
                    .sensorType("sensorType") //
                    .address(0) //
                    .value(0);

    @Test
    @DisplayName("빌더 패턴 테스트")
    void buildTest() throws IllegalArgumentException, IllegalAccessException {
        // given
        Field[] fields = Data.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
        }

        // when
        Data data = DEFAULT_DATA_BUILDER.build();

        // then
        for (Field field : fields) {
            String type = field.getType().getSimpleName();
            if (type.equals("Integer")) {
                assertEquals(0, field.get(data));
                continue;
            }
            assertEquals(field.getName(), field.get(data));
        }
    }

    @Test
    @DisplayName("getter를 호출하면 필드 값을 반환한다")
    void getterTest() {
        // given
        Data data = DEFAULT_DATA_BUILDER.build();

        // when
        // then
        assertAll( //
                () -> assertEquals("deviceEui", data.getDeviceEui()), //
                () -> assertEquals("site", data.getSite()), //
                () -> assertEquals(0, data.getValue()) //
        );
    }

    @Test
    @DisplayName("데이터 객체의 동등성 테스트")
    void equalityTest() {
        // given
        Data data = DEFAULT_DATA_BUILDER.build();
        Data data2 = DEFAULT_DATA_BUILDER.build();
        Data data3 = Data.builder() //
                .deviceEui("deviceEui") //
                .site("site") //
                .branch("branch") //
                .place("place") //
                .sensorType("sensorType") //
                .address(0) //
                .value(1) //
                .build();

        // when
        // then
        assertAll( //
                () -> assertEquals(data, data2), //
                () -> assertNotEquals(data, data3), //
                () -> assertNotEquals(data2, data3) //
        );
    }
}
