package com.nhnacademy.aiot.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Data {

    private final String deviceEui;
    private final String site;
    private final String branch;
    private final String place;
    private final String sensorType;
    private final Integer address;
    private final Integer value;
}
