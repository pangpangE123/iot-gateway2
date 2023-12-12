package com.nhnacademy.aiot.broker;

public interface MODBUSBroker {
    /**
     * BufferedInputStream이 byte-input stream으로부터 주어진 offset을 시작으로 byte 배열의 byte를
     * 읽어오도록 합니다.
     * 
     * @param b   읽어올 버퍼
     * @param off byte를 저장하기 시작할 오프셋
     * @param len 읽어올 byte의 최대 길이
     */
    public void read(byte[] b, int off, int len);

    /**
     * BufferedOutputStream이 output stream에 byte 배열의 length만큼 byte를 쓰도록 합니다.
     * 
     * @param b write할 데이터
     */
    public void write(byte[] b);

}
