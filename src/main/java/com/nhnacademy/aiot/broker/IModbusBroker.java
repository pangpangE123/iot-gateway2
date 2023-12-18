package com.nhnacademy.aiot.broker;

/*
 * Modbus 클라이언트 간의 메세지를 조정하는 클래스입니다.
 */
public interface IModbusBroker {

    /**
     * input stream에서 읽어온 byte 배열을 반환합니다.
     *
     * @return 읽어온 데이터
     */
    public byte[] read();

    /**
     * output stream에 byte 배열의 length만큼 byte를 쓰도록 합니다.
     *
     * @param b write할 데이터
     */
    public void write(byte[] b);

    /**
     * 브로커의 아이디를 가져옵니다.
     *
     * @return 브로커의 아이디를 반환합니다.
     */
    public String getId();
}
