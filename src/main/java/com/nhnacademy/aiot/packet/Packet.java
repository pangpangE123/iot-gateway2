package com.nhnacademy.aiot.packet;

import java.util.Date;

import org.json.JSONObject;

import lombok.extern.slf4j.Slf4j;

/*
 * 데이터를 담을 클래스입니다.
 */
@Slf4j
public class Packet extends JSONObject {
    private Date creationDate;

    public Packet() {
        super();
        creationDate = new Date();
        log.debug("Packet: 패킷이 생성되었습니다. (생성시간: " + getCreationDate() + ")");
    }

    /**
     * 인스턴스가 생성된 시간을 반환합니다.
     * 
     * @return 인스턴스가 생성된 시간
     */
    public Date getCreationDate() {
        return creationDate;
    }
}
