package com.nhnacademy.aiot.repository;

import com.nhnacademy.aiot.broker.IModbusBroker;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 시스템 메모리에 <code>Modbus Broker</code> 객체를 저장하는 레포지토리 클래스입니다.
 * <p>
 * 인메모리 데이터 레포지토리는 싱글톤 패턴으로 구현되어 있습니다.
 * <p>
 * 레포지토리 인스턴스를 사용하기 위해서 <code>getInstance()</code>를 호출해야합니다.
 */
@Slf4j
public class InMemoryModbusBrokerRepository implements Repository<IModbusBroker> {
    public static final String NULL_CAN_NOT_BE_USED = "null은 입력할 수 없습니다.";

    private static InMemoryModbusBrokerRepository instance;
    private Map<String, IModbusBroker> brokers;

    private InMemoryModbusBrokerRepository() {
        brokers = new HashMap<>();
    }

    public static InMemoryModbusBrokerRepository getInstance() {
        if (instance == null) {
            instance = new InMemoryModbusBrokerRepository();
        }
        return instance;
    }

    @Override
    public boolean save(IModbusBroker broker) {
        checkNull(broker);

        if (brokers.containsKey(broker.getId())) {
            return false;
        }

        brokers.put(broker.getId(), broker);
        return true;
    }

    @Override
    public IModbusBroker findById(String id) {
        checkNull(id);

        return brokers.get(id);
    }

    @Override
    public boolean remove(IModbusBroker broker) {
        checkNull(broker);

        return remove(broker.getId());
    }

    @Override
    public boolean remove(String id) {
        checkNull(id);

        return brokers.remove(id) != null;
    }

    private static void checkNull(Object object) {
        if (object == null) {
            log.error(NULL_CAN_NOT_BE_USED);
            throw new IllegalArgumentException(NULL_CAN_NOT_BE_USED);
        }
    }
}
