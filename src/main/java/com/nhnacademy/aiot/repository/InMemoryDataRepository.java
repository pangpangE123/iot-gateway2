package com.nhnacademy.aiot.repository;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    public boolean remove(Data object) {
        return false;
    }

    @Override
    public boolean remove(String id) {
        return false;
    }
}
