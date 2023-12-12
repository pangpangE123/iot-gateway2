package com.nhnacademy.aiot.info;

/**
 * 메세지 송수신 정보를 가져올수있는 인터페이스 입니다.
 */
public interface InfoProvider {
    /**
     * 메세지 상태 정보를 가져옵니다.
     * 
     * @return 상태가 담긴 Info 객체를 반환합니다.
     */
    Info getInfo();
}
