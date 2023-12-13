package com.nhnacademy.aiot.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

class InMemoryDataRepositoryTest {

    @BeforeEach
    void resetRepository() throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 싱글톤 패턴이기 때문에 생성자를 호출하여 필드의 인스턴스를 초기화한다.
        Constructor<InMemoryDataRepository> constructor = InMemoryDataRepository.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Field field = InMemoryDataRepository.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(null, constructor.newInstance());
    }

    @Test
    @DisplayName("레포지토리는 싱글톤으로 관리된다")
    void getInstanceTest() {
        // given
        // when
        InMemoryDataRepository instance = InMemoryDataRepository.getInstance();
        InMemoryDataRepository instance2 = InMemoryDataRepository.getInstance();

        // then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(instance),
                () -> Assertions.assertEquals(instance, instance2)
        );
    }

    // TODO save를 하면 객체를 저장한다.
    // TODO null 객체를 save하면 예외를 발생시킨다.
    // TODO 갱신되지 않은 데이터를 넣으면 save하지 않는다.
    // TODO findById를 하면 해당하는 객체를 반환한다.
}
