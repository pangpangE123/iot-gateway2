package com.nhnacademy.aiot.repository;

public interface Repository<T> {

    void save(T t);

    T findById(String id);

    void remove(T t);

    void remove(String id);
}
