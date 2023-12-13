package com.nhnacademy.aiot.repository;

public class InMemoryDataRepository implements Repository<Data> {

    private static InMemoryDataRepository instance;

    private InMemoryDataRepository() {
    }

    public static synchronized InMemoryDataRepository getInstance() {
        if (instance == null) {
            instance = new InMemoryDataRepository();
        }
        return instance;
    }

    @Override
    public boolean save(Data data) {
        return false;
    }

    @Override
    public Data findById(String id) {
        return null;
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
