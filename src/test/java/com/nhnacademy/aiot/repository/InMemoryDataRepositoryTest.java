package com.nhnacademy.aiot.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
                () -> assertNotNull(instance),
                () -> assertEquals(instance, instance2)
        );
    }

    @Test
    @DisplayName("save를 하면 객체를 저장한다")
    @SuppressWarnings("unchecked")
    void saveTest() throws NoSuchFieldException, IllegalAccessException {
        // given
        InMemoryDataRepository repository = InMemoryDataRepository.getInstance();
        Field field = InMemoryDataRepository.class.getDeclaredField("datas");
        field.setAccessible(true);
        ConcurrentHashMap<String, Data> datas = (ConcurrentHashMap<String, Data>) field.get(repository);

        String deviceEui = "DeviceEui";
        Data mockData = mock(Data.class);
        Data mockData2 = mock(Data.class);
        when(mockData.getDeviceEui()).thenReturn(deviceEui);
        when(mockData2.getDeviceEui()).thenReturn(deviceEui);

        // when
        boolean expected = repository.save(mockData);
        boolean expected2 = repository.save(mockData);

        // then
        Assertions.assertAll(
                () -> assertEquals(1, datas.size()),
                () -> assertEquals(mockData, datas.get(deviceEui)),
                () -> assertThrows(IllegalArgumentException.class, () -> repository.save(null)),
                () -> assertTrue(expected),
                () -> assertFalse(expected2)
        );
    }

    @Test
    @DisplayName("findById를 하면 해당하는 객체를 반환한다")
    void findByIdTest() {
        // given
        InMemoryDataRepository repository = InMemoryDataRepository.getInstance();
        Data expected = mock(Data.class);
        String deviceEui = "DeviceEui";
        when(expected.getDeviceEui()).thenReturn(deviceEui);
        repository.save(expected);

        // when
        Data actual = repository.findById(deviceEui);
        Executable executable = () -> repository.findById(null);

        // then
        assertThrows(IllegalArgumentException.class, executable);
        assertEquals(expected, actual);
    }
}
