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
    private Repository<Data> repository;

    @BeforeEach
    void resetRepository() throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 싱글톤 패턴이기 때문에 생성자를 호출하여 필드의 인스턴스를 초기화한다.
        Constructor<InMemoryDataRepository> constructor = InMemoryDataRepository.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Field field = InMemoryDataRepository.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(null, constructor.newInstance());

        repository = InMemoryDataRepository.getInstance();
    }

    @DisplayName("레포지토리는 싱글톤")
    @Test
    void repositoryIsSingleton() {
        // given
        // when
        Repository<Data> instance = InMemoryDataRepository.getInstance();

        // then
        Assertions.assertAll(
                () -> assertNotNull(repository),
                () -> assertEquals(repository, instance)
        );
    }

    @DisplayName("정상적인 객체라면 저장")
    @Test
    @SuppressWarnings("unchecked")
    void successToSaveIfObjectIsNormal() throws NoSuchFieldException, IllegalAccessException {
        // given
        Field field = InMemoryDataRepository.class.getDeclaredField("datas");
        field.setAccessible(true);
        ConcurrentHashMap<String, Data> datas = (ConcurrentHashMap<String, Data>) field.get(repository);

        String deviceEui = "DeviceEui";
        Data mockData = mock(Data.class);
        when(mockData.getDeviceEui()).thenReturn(deviceEui);

        // when
        boolean expected = repository.save(mockData);

        // then
        Assertions.assertAll(
                () -> assertEquals(1, datas.size()),
                () -> assertEquals(mockData, datas.get(deviceEui)),
                () -> assertTrue(expected)
        );
    }

    @DisplayName("null을 저장하면 예외 발생")
    @Test
    void throwExceptionWhenTryToSaveNull() {
        // given
        // when
        Executable executable = () -> repository.save(null);

        // then
        assertThrows(IllegalArgumentException.class, executable);
    }

    @DisplayName("동등한 객체를 저장하면 실패")
    @Test
    void failToSaveIfObjectIsNotChanged() {
        // given
        Data mockData = mock(Data.class);
        when(mockData.getDeviceEui()).thenReturn("DeviceEui");
        repository.save(mockData);

        // when
        boolean expected = repository.save(mockData);

        // then
        assertFalse(expected);
    }

    @DisplayName("객체를 찾으면 반환")
    @Test
    void returnObjectIfObjectIsFound() {
        // given
        Data expected = mock(Data.class);
        String deviceEui = "DeviceEui";
        when(expected.getDeviceEui()).thenReturn(deviceEui);
        repository.save(expected);

        // when
        Data actual = repository.findById(deviceEui);

        // then
        assertEquals(expected, actual);
    }

    @DisplayName("null을 찾으려고하면 예외 발생")
    @Test
    void throwExceptionWhenTryToFindNull() {
        // given
        // when
        Executable executable = () -> repository.findById(null);

        // then
        assertThrows(IllegalArgumentException.class, executable);
    }

    @DisplayName("아이디에 해당하는 객체 삭제")
    @Test
    void successToRemoveIfIdIsExist() {
        // given
        String deviceEui = "DeviceEui";
        Data mockData = mock(Data.class);
        when(mockData.getDeviceEui()).thenReturn(deviceEui);
        repository.save(mockData);

        // when
        boolean actual = repository.remove(deviceEui);

        // then
        assertTrue(actual);
    }

    @DisplayName("아이디로 null을 삭제하려고하면 예외 발생")
    @Test
    void throwExceptionWhenTryToRemoveNullString() {
        // given
        // when
        Executable executable = () -> repository.remove((String) null);

        // then
        assertThrows(IllegalArgumentException.class, executable);
    }

    @DisplayName("동일한 객체 삭제")
    @Test
    void successToRemoveIfDataIsExist() {
        // given
        Data mockData = mock(Data.class);
        when(mockData.getDeviceEui()).thenReturn("DeviceEui");
        repository.save(mockData);

        // when
        boolean actual = repository.remove(mockData);

        // then
        assertTrue(actual);
    }

    @DisplayName("null을 삭제하려고하면 예외 발생")
    @Test
    void throwExceptionWhenTryToRemoveNullData() {
        // given
        // when
        Executable executable = () -> repository.remove((Data) null);

        // then
        assertThrows(IllegalArgumentException.class, executable);
    }
}
