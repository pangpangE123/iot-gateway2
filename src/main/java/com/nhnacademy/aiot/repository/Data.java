package com.nhnacademy.aiot.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 데이터를 담는 엔티티 클래스입니다.
 * <p>
 * 데이터에 담긴 내용이 동일하면 동일한 객체로 판단합니다.
 * <p>
 * Getter와 Builder 패턴을 지원합니다.
 */
@Getter
@Builder
@AllArgsConstructor
public class Data {

    private final String deviceEui;
    private final String site;
    private final String branch;
    private final String place;
    private final String sensorType;
    private final Integer address; // Modbus 전용 데이터
    private final Integer value;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((deviceEui == null) ? 0 : deviceEui.hashCode());
        result = prime * result + ((site == null) ? 0 : site.hashCode());
        result = prime * result + ((branch == null) ? 0 : branch.hashCode());
        result = prime * result + ((place == null) ? 0 : place.hashCode());
        result = prime * result + ((sensorType == null) ? 0 : sensorType.hashCode());
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Data other = (Data) obj;
        if (deviceEui == null) {
            if (other.deviceEui != null)
                return false;
        } else if (!deviceEui.equals(other.deviceEui))
            return false;
        if (site == null) {
            if (other.site != null)
                return false;
        } else if (!site.equals(other.site))
            return false;
        if (branch == null) {
            if (other.branch != null)
                return false;
        } else if (!branch.equals(other.branch))
            return false;
        if (place == null) {
            if (other.place != null)
                return false;
        } else if (!place.equals(other.place))
            return false;
        if (sensorType == null) {
            if (other.sensorType != null)
                return false;
        } else if (!sensorType.equals(other.sensorType))
            return false;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
}
