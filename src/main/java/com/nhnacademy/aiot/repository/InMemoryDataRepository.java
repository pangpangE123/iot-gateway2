package com.nhnacademy.aiot.repository;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 시스템 메모리에 <code>Data</code> 객체를 저장하는 레포지토리 클래스입니다.
 * <p>
 * 인메모리 데이터 레포지토리는 싱글톤 패턴으로 구현되어 있습니다.
 * <p>
 * 레포지토리 인스턴스를 사용하기 위해서 <code>getInstance()</code>를 호출해야합니다.
 */
@Slf4j
public class InMemoryDataRepository implements Repository<Data> {
    public static final String NULL_CAN_NOT_BE_USED = "null은 입력할 수 없습니다.";

    private static InMemoryDataRepository instance;
    private final Map<String, Data> datas;

    private InMemoryDataRepository() {
        datas = new ConcurrentHashMap<>();
    }

    public static synchronized InMemoryDataRepository getInstance() {
        if (instance == null) {
            instance = new InMemoryDataRepository();
        }
        return instance;
    }

    @Override
    public boolean save(Data data) {
        if (data == null) {
            log.error(NULL_CAN_NOT_BE_USED);
            throw new IllegalArgumentException(NULL_CAN_NOT_BE_USED);
        }

        String deviceEui = data.getDeviceEui();
        if (datas.containsKey(deviceEui) && data.equals(datas.get(deviceEui))) {
            return false;
        }
        datas.put(deviceEui, data);
        return true;
    }

    @Override
    public Data findById(String id) {
        if (id == null) {
            log.error(NULL_CAN_NOT_BE_USED);
            throw new IllegalArgumentException(NULL_CAN_NOT_BE_USED);
        }

        return datas.get(id);
    }

    @Override
    public boolean remove(Data data) {
        if (data == null) {
            log.error(NULL_CAN_NOT_BE_USED);
            throw new IllegalArgumentException(NULL_CAN_NOT_BE_USED);
        }

        return remove(data.getDeviceEui());
    }

    @Override
    public boolean remove(String id) {
        if (id == null) {
            log.error(NULL_CAN_NOT_BE_USED);
            throw new IllegalArgumentException(NULL_CAN_NOT_BE_USED);
        }

        return datas.remove(id) != null;
    }
}
