package com.nhnacademy.aiot.repository;

import com.nhnacademy.aiot.broker.IModbusBroker;
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

class InMemoryModbusBrokerRepositoryTest {
    InMemoryModbusBrokerRepository repository;

    @BeforeEach
    void setUp() throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<InMemoryModbusBrokerRepository> constructor = InMemoryModbusBrokerRepository.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Field field = InMemoryModbusBrokerRepository.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(null, constructor.newInstance());

        repository = InMemoryModbusBrokerRepository.getInstance();
    }

    @DisplayName("레포지토리는 싱글톤")
    @Test
    void singletonTest() {
        // given
        // when
        InMemoryModbusBrokerRepository instance = InMemoryModbusBrokerRepository.getInstance();

        // then
        assertEquals(repository, instance);
    }

    @Test
    @SuppressWarnings("unchecked")
    void save() throws NoSuchFieldException, IllegalAccessException {
        Field field = InMemoryModbusBrokerRepository.class.getDeclaredField("brokers");
        field.setAccessible(true);
        Map<String, IModbusBroker> brokers = (Map<String, IModbusBroker>) field.get(repository);

        IModbusBroker mockBroker = mock(IModbusBroker.class);
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
        IModbusBroker mockBroker = mock(IModbusBroker.class);
        when(mockBroker.getId()).thenReturn("brokerId");

        repository.save(mockBroker);

        IModbusBroker actual = repository.findById(mockBroker.getId());
        IModbusBroker actual2 = repository.findById("notSaved");

        assertAll(
                () -> assertEquals(mockBroker, actual),
                () -> assertNull(actual2),
                () -> assertThrows(IllegalArgumentException.class, () -> repository.findById(null))
        );
    }

    @Test
    void remove() {
        IModbusBroker mockBroker = mock(IModbusBroker.class);
        when(mockBroker.getId()).thenReturn("brokerId");
        repository.save(mockBroker);

        boolean actual = repository.remove(mockBroker);
        boolean actual2 = repository.remove(mockBroker);

        assertAll(
                () -> assertTrue(actual),
                () -> assertFalse(actual2),
                () -> assertThrows(IllegalArgumentException.class, () -> repository.remove((String) null)),
                () -> assertThrows(IllegalArgumentException.class, () -> repository.remove((IModbusBroker) null))
        );
    }
}
