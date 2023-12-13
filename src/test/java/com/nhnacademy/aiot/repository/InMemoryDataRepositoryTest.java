package com.nhnacademy.aiot.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

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
        Data mockData = Mockito.mock(Data.class);
        Data mockData2 = Mockito.mock(Data.class);
        Mockito.when(mockData.getDeviceEui())
                .thenReturn(deviceEui);
        Mockito.when(mockData2.getDeviceEui())
                .thenReturn(deviceEui);

        // when
        boolean expected = repository.save(mockData);
        boolean expected2 = repository.save(mockData);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, datas.size()),
                () -> Assertions.assertEquals(mockData, datas.get(deviceEui)),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> repository.save(null)),
                () -> Assertions.assertTrue(expected),
                () -> Assertions.assertFalse(expected2)
        );
    }
    // TODO findById를 하면 해당하는 객체를 반환한다.
}
