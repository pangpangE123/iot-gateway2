package com.nhnacademy.aiot.info;

import java.util.EnumMap;

/**
 * 페킷 정보 수집을 위한 클래스입니다.
 */
public class Info {
    private final EnumMap<PacketType, Long> infos;

    // enum을 키로 사용하는 EnumMap을 통해 빠른 검색과 삽입 성능을 제공합니다.
    public Info() {
        infos = new EnumMap<>(PacketType.class);
    }

    /**
     * 키에 해당하는 값(페킷 송수신 정보)을 반환합니다.
     * 
     * @param key 확인할려는 키 입니다.
     * @return 수집된 값(페킷 송수신 정보)을 반환합니다.
     *         <p>
     *         존재하지 않는 키는 <code>0</code> 값을 반환합니다.
     */
    public long get(PacketType key) {
        if (!infos.containsKey(key)) {
            return 0;
        }
        return infos.get(key);
    }

    /**
     * 키가 존재하지 않으면 키를 추가하고 대응 값 1을 추가합니다.
     * <P>
     * 키가 존재하면 키에 해당 하는 값을 +1 증가합니다.
     * 
     * @param key 증가할 정보의 키 입니다.
     */
    public void increase(PacketType key) {
        infos.computeIfAbsent(key, e -> 1L);
        infos.computeIfPresent(key, (k, value) -> value + 1);
    }
}
