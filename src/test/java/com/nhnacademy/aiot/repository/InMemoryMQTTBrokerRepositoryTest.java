package com.nhnacademy.aiot.repository;

import com.nhnacademy.aiot.broker.IMQTTBroker;
import com.nhnacademy.aiot.broker.IMQTTBroker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InMemoryMQTTBrokerRepositoryTest {
    InMemoryMQTTBrokerRepository repository;

    @BeforeEach
    void setUp() throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<InMemoryMQTTBrokerRepository> constructor = InMemoryMQTTBrokerRepository.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Field field = InMemoryMQTTBrokerRepository.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(null, constructor.newInstance());

        repository = InMemoryMQTTBrokerRepository.getInstance();
    }

    @DisplayName("레포지토리는 싱글톤")
    @Test
    void singletonTest() {
        // given
        // when
        InMemoryMQTTBrokerRepository instance = InMemoryMQTTBrokerRepository.getInstance();

        // then
        assertEquals(repository, instance);
    }

    @Test
    @SuppressWarnings("unchecked")
    void save() throws NoSuchFieldException, IllegalAccessException {
        Field field = InMemoryMQTTBrokerRepository.class.getDeclaredField("brokers");
        field.setAccessible(true);
        Map<String, IMQTTBroker> brokers = (Map<String, IMQTTBroker>) field.get(repository);

        IMQTTBroker mockBroker = mock(IMQTTBroker.class);
        when(mockBroker.getId()).thenReturn("brokerId");

        boolean actual = repository.save(mockBroker);
        boolean actual2 = repository.save(mockBroker);

        assertAll(
                () -> assertEquals(1, brokers.size()),
                () -> assertTrue(actual),
                () -> assertFalse(actual2),
                () -> assertThrows(IllegalArgumentException.class, () -> repository.save(null))
        );
    }

    @Test
    void findById() {
        IMQTTBroker mockBroker = mock(IMQTTBroker.class);
        when(mockBroker.getId()).thenReturn("brokerId");

        repository.save(mockBroker);

        IMQTTBroker actual = repository.findById(mockBroker.getId());
        IMQTTBroker actual2 = repository.findById("notSaved");

        assertAll(
                () -> assertEquals(mockBroker, actual),
                () -> assertNull(actual2),
                () -> assertThrows(IllegalArgumentException.class, () -> repository.findById(null))
        );
    }

    @Test
    void remove() {
        IMQTTBroker mockBroker = mock(IMQTTBroker.class);
        when(mockBroker.getId()).thenReturn("brokerId");
        repository.save(mockBroker);

        boolean actual = repository.remove(mockBroker);
        boolean actual2 = repository.remove(mockBroker);

        assertAll(
                () -> assertTrue(actual),
                () -> assertFalse(actual2),
                () -> assertThrows(IllegalArgumentException.class, () -> repository.remove((String) null)),
                () -> assertThrows(IllegalArgumentException.class, () -> repository.remove((IMQTTBroker) null))
        );
    }
}
