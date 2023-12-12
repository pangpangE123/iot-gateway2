package com.nhnacademy.aiot.repository;

/**
 * 영속성 관리를 위한 인터페이스를 제공하는 저장소 클래스입니다.
 * <p>
 * 제네릭으로 저장할 데이터의 타입을 명시합니다.
 */
public interface Repository<T> {

    /**
     * 객체를 저장합니다.
     * 
     * @param object 저장할 객체입니다.
     * @return 객체를 저장하면 <code>true</code>를 반환합니다.
     *         <p>
     *         객체를 저장하지 못하면 <code>false</code>를 반환합니다.
     */
    boolean save(T object);

    /**
     * 아이디를 통해 저장된 객체를 찾습니다.
     * 
     * @param id 찾을 객체의 아이디입니다.
     * @return 찾은 객체를 반환합니다.
     */
    T findById(String id);

    /**
     * 저장된 객체를 지웁니다.
     * 
     * @param object 지울 객체입니다.
     * @return 객체를 지우면 <code>true</code>를 반환합니다.
     *         <p>
     *         객체를 지우지 못하면 <code>false</code>를 반환합니다.
     */
    boolean remove(T object);

    /**
     * 저장된 객체를 지웁니다.
     * 
     * @param id 지울 객체의 아이디입니다.
     * @return 객체를 지우면 <code>true</code>를 반환합니다.
     *         <p>
     *         객체를 지우지 못하면 <code>false</code>를 반환합니다.
     */
    boolean remove(String id);
}
